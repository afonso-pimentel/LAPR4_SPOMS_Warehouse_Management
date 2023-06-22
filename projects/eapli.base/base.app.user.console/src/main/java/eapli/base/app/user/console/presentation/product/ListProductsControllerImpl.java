package eapli.base.app.user.console.presentation.product;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.type.CollectionType;
import com.fasterxml.jackson.databind.type.TypeFactory;
import eapli.base.app.user.console.BaseUserApp;
import eapli.base.productmanagement.domain.Product;
import enums.MessageCode;
import enums.ProtocolVersion;
import models.ProductCatalogRequest;
import models.ProductDTO;
import models.SpomsPacketBuilder;
import java.util.ArrayList;
import java.util.List;

public class ListProductsControllerImpl implements ListProductsController{

    @Override
    public Iterable<ProductDTO> sortAndFilterProducts(String filterField, String filter, String sortField, Boolean ascending) {
        try{
            var objectMapper = new ObjectMapper();

            var productCatalogRequest = new ProductCatalogRequest();
            productCatalogRequest.filter = filter;
            productCatalogRequest.filterField = filterField;
            productCatalogRequest.sortField = sortField;
            productCatalogRequest.ascending = ascending;

            var requestData = objectMapper.writeValueAsString(productCatalogRequest);

            var request = new SpomsPacketBuilder()
                                       .messageCode(MessageCode.PRODUCT_CATALOG_REQUEST)
                                       .protocolVersion(ProtocolVersion.VERSION_1)
                                       .data(requestData)
                                       .build();

            var response = BaseUserApp.orderServerClient.SpomsCommunication().getResponse(request, MessageCode.PRODUCT_CATALOG_RESPONSE);

            CollectionType typeReference =
                    TypeFactory.defaultInstance().constructCollectionType(List.class, ProductDTO.class);

            return objectMapper.readValue(response.data(), typeReference);

        } catch(Exception exception){
            return new ArrayList<>();
        }
    }
}
