/*
 * Copyright (c) 2021-2022 the original author or authors.
 *
 * MIT License
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

import com.fasterxml.jackson.databind.ObjectMapper;
import eapli.base.infrastructure.persistence.PersistenceContext;
import eapli.base.ordersmanagement.application.ChangeOrderStatusService;
import eapli.base.ordersmanagement.application.ListOrderService;
import eapli.base.ordersmanagement.domain.OrderLine;
import eapli.base.ordersmanagement.domain.OrderStatus;
import eapli.base.ordersmanagement.domain.Order_;
import eapli.base.productmanagement.domain.Product;
import eapli.base.productmanagement.repositories.ProductRepository;
import eapli.base.warehousemanagement.application.AgvService;
import eapli.base.warehousemanagement.domain.AgvStatus;
import enums.MessageCode;
import enums.ProtocolVersion;
import models.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

/**
 * AGV Manager set of automated tasks
 */
public class AGVManagerAutomatedTasks extends Thread {
    private final ListOrderService listOrderService;
    private final AgvService agvService;
    private final ProductRepository productRepository;

    private final ChangeOrderStatusService changeOrderStatusService;

    /**
     * Initializes a new instance of AGVManagerAutomatedTasks
     */
    public AGVManagerAutomatedTasks(){
        listOrderService = new ListOrderService(PersistenceContext.repositories().orders(), PersistenceContext.repositories().customers());
        agvService = new AgvService(PersistenceContext.repositories().agvs());
        productRepository = PersistenceContext.repositories().products();
        changeOrderStatusService = new ChangeOrderStatusService(PersistenceContext.repositories().orders());
    }

    /**
     * @inheritDoc
     */
    public void run() {
        var mapper = new ObjectMapper();

        while (true) {
            try{
                TimeUnit.SECONDS.sleep(1);

                Iterable<Order_> listOfRegistedOrders = listOrderService.allRegisteredOrders();

                if(listOfRegistedOrders.iterator() != null && listOfRegistedOrders.iterator().hasNext()){

                    var listOrders = new ArrayList<Order_>();

                    listOfRegistedOrders.forEach(listOrders::add);

                    listOrders.sort(Comparator.comparing(Order_::orderDate).reversed());

                    Order_ orderToBeDispatched = listOrders.get(0);

                    Iterable<Product> orderProducts = getOrderProducts(orderToBeDispatched.orderLines());

                    var choosenAGVDigitalTwin = AGVManagerCaching.AgvStatusCache.StatusCache.getAllAgvStatus().stream().filter(
                                    agvDataResponse -> agvDataResponse.status != AgvStatus.WORKING
                                            && agvDataResponse.status != AgvStatus.SHUTTING_DOWN
                                            && agvDataResponse.status != AgvStatus.LOW_BATTERY)
                            .findFirst();

                    if(choosenAGVDigitalTwin.isPresent()){

                        var agvTwinCommunication = AGVManager.getDigitalTwin(choosenAGVDigitalTwin.get().id);

                        if(agvTwinCommunication != null){

                            var orderTaskAssignment = new OrderTaskAssignment(orderToBeDispatched.identity(),
                                    choosenAGVDigitalTwin.get().id, mapFromDomainEntityToDTO(orderProducts));

                            var packetData = mapper.writeValueAsString(orderTaskAssignment);

                            SpomsPacket request = new SpomsPacketBuilder()
                                    .messageCode(MessageCode.ORDER_ASSIGNMENT)
                                    .protocolVersion(ProtocolVersion.VERSION_1)
                                    .data(packetData)
                                    .build();

                            var response = agvTwinCommunication.spomsCommunication.getResponse(request, MessageCode.ORDER_ASSIGNMENT_RESPONSE);

                            var orderTaskAssignmentResponse = mapper.readValue(response.data(), OrderTaskAssignmentResponse.class);

                            if(!orderTaskAssignmentResponse.HasErrors()){
                                changeOrderStatusService.changeOrderStatus(orderToBeDispatched, OrderStatus.IN_PREPARATION);
                            }else{
                                System.out.println("An error has occurred when sending a new task to AGV with id "
                                        + choosenAGVDigitalTwin.get().id +
                                        ". Error: " + orderTaskAssignmentResponse.ErrorMessage());
                            }

                        }

                    }
                }

            }catch (InterruptedException e) {
                System.out.println("Interrupted error!");
            } catch (IOException e) {
                System.out.println("IO error!");
            } catch (TimeoutException e) {
                System.out.println("Time out error!");
            } catch (Exception e) {
                System.out.println("Error: " + e.getMessage());
            }
        }
    }

    private List<ProductDTO> mapFromDomainEntityToDTO(Iterable<Product> products){
        var listOfProductsDTO = new ArrayList<ProductDTO>();

        for(Product product : products){
            var productDTO = new ProductDTO();

            productDTO.price = product.price() != null ? product.price().amountAsDouble() : 0;
            productDTO.category = product.category() != null ? product.category().code().code() : "";
            productDTO.internalCode = product.identity().toString();
            productDTO.shortDescription = product.shortDescription() != null ? product.shortDescription().code() : "";
            productDTO.brand = product.brand() != null ? product.brand().toString() : "";
            productDTO.pickedUp = false;

            var storageAreaDTO = new StorageAreaDTO();

            if(product.storageArea() != null){
                storageAreaDTO.aisle = product.storageArea().aisle();
                storageAreaDTO.row = product.storageArea().row();
                storageAreaDTO.shelf = product.storageArea().shelf();
            }else{
                storageAreaDTO.aisle = 1L;
                storageAreaDTO.row = 1L;
                storageAreaDTO.shelf = 1L;
            }

            productDTO.position = storageAreaDTO;

            listOfProductsDTO.add(productDTO);
        }
        return listOfProductsDTO;
    }

    private Iterable<Product> getOrderProducts(Iterable<OrderLine> listOfOrderLines){
        var products = new ArrayList<Product>();

        for(OrderLine orderLine : listOfOrderLines){
            Optional<Product> product = productRepository.find(orderLine.productId());

            if(product.isPresent()){
                products.add(product.get());
            }
        }

        return products;
    }
}
