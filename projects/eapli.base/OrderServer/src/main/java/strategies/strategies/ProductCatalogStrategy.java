package strategies.strategies;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import eapli.base.Application;
import eapli.base.infrastructure.persistence.PersistenceContext;
import eapli.base.ordersmanagement.application.ChangeOrderStatusService;
import eapli.base.ordersmanagement.application.algorithms.AlgorithmsStrategy;
import eapli.base.ordersmanagement.application.algorithms.AlgorithmsStrategyFactory;
import eapli.base.ordersmanagement.application.algorithms.AlgorithmsStrategyFactoryImpl;
import eapli.base.ordersmanagement.application.algorithms.AlgorithmsStrategyList;
import eapli.base.ordersmanagement.domain.OrderStatus;
import eapli.base.ordersmanagement.domain.Order_;
import eapli.base.productmanagement.domain.Product;
import eapli.base.productmanagement.repositories.ProductRepository;
import eapli.base.warehousemanagement.application.AgvService;
import eapli.base.warehousemanagement.domain.Agv;
import eapli.base.warehousemanagement.domain.AgvStatus;
import eapli.base.warehousemanagement.repositories.AgvRepository;
import enums.MessageCode;
import enums.ProtocolVersion;
import models.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


public class ProductCatalogStrategy implements SpomsStrategy{

    @Override
    public SpomsPacket PerformStrategy(SpomsPacket packetToHandle) throws JsonProcessingException {
        var packetData = "";
        var objectMapper = new ObjectMapper();

        try {

            var productCatalogRequest = objectMapper.readValue(packetToHandle.data(), ProductCatalogRequest.class);

            ProductRepository productRepository = PersistenceContext.repositories().products();

            var activeProducts = productRepository.filterSortActive(productCatalogRequest.filterField, productCatalogRequest.filter,
                                                        productCatalogRequest.sortField, productCatalogRequest.ascending);

            var productDTOs = mapFromDomainEntitiesToDTOs(activeProducts);

            packetData = objectMapper.writeValueAsString(productDTOs);

        }catch (Exception ex)
        {
            packetData = objectMapper.writeValueAsString(new ArrayList<Product>());
        }

        return new SpomsPacketBuilder()
                .messageCode(MessageCode.PRODUCT_CATALOG_RESPONSE)
                .protocolVersion(ProtocolVersion.VERSION_1)
                .data(packetData)
                .build();
    }

    private Iterable<ProductDTO> mapFromDomainEntitiesToDTOs(Iterable<Product> products){
        List productDTOList = new ArrayList<>();

        for(var product : products){
            var productDTO = new ProductDTO();
            productDTO.internalCode = product.identity().code();
            productDTO.brand = product.brand().toString();
            productDTO.category = product.category().code().code();
            productDTO.shortDescription = product.shortDescription().code();
            productDTO.price = product.price().amountAsDouble();

            productDTOList.add(productDTO);
        }

        return productDTOList;
    }
}
