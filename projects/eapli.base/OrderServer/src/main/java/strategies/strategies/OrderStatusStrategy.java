package strategies.strategies;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import eapli.base.customerusermanagement.domain.VatCode;
import eapli.base.infrastructure.persistence.PersistenceContext;
import eapli.base.ordersmanagement.domain.Order_;

import eapli.base.ordersmanagement.repositories.OrderRepository;
import enums.MessageCode;
import enums.ProtocolVersion;
import models.*;


import java.util.ArrayList;
import java.util.List;

public class OrderStatusStrategy implements SpomsStrategy{
    private final OrderRepository orderRepository;

    /**
     * Initializes a new instance of OrderStatusStrategyRequest
     * @param orderRepository OrderRepository
     */
    public OrderStatusStrategy(OrderRepository orderRepository){
        if(orderRepository == null)
            throw new IllegalArgumentException("OrderRepository cannot be null");

        this.orderRepository = orderRepository;
    }

    @Override
    public SpomsPacket PerformStrategy(SpomsPacket packetToHandle) throws JsonProcessingException {
        System.out.println("Received to get order status");

        var packetData = "";
        var objectMapper = new ObjectMapper();

        try {

            var userOrderStatus = objectMapper.readValue(packetToHandle.data(), UserOrder.class);

            OrderRepository orderRepository = PersistenceContext.repositories().orders();

            var customerOrders = orderRepository.findCustomerOrders(VatCode.valueOf(userOrderStatus.VATCode));

            var orderDTOS = mapFromDomainEntitiesToDTOs(customerOrders);

            packetData = objectMapper.writeValueAsString(orderDTOS);

        }catch (Exception ex)
        {
            packetData = objectMapper.writeValueAsString(new ArrayList<Order_>());
        }

        return new SpomsPacketBuilder()
                .messageCode(MessageCode.ORDER_STATUS_RESPONSE)
                .protocolVersion(ProtocolVersion.VERSION_1)
                .data(packetData)
                .build();
    }

    private Iterable<OrderDTO> mapFromDomainEntitiesToDTOs(Iterable<Order_> orders){
        List orderDTOList = new ArrayList<>();

        for(var order : orders){
            var orderDTO = new OrderDTO();
            orderDTO.orderID = order.identity();
            orderDTO.orderStatus = order.orderStatus();
            orderDTO.orderDate = order.orderDate();

            orderDTOList.add(orderDTO);
        }

        return orderDTOList;
    }
}
