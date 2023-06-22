package eapli.base.app.user.console.presentation.order;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.type.CollectionType;
import com.fasterxml.jackson.databind.type.TypeFactory;
import eapli.base.app.user.console.BaseUserApp;
import eapli.base.app.user.console.presentation.order.ListOrderController;
import eapli.base.customerusermanagement.domain.VatCode;
import enums.MessageCode;
import enums.ProtocolVersion;
import models.*;

import java.util.ArrayList;
import java.util.List;


public class ListOrderControllerImpl implements ListOrderController {

    @Override
    public Iterable<OrderDTO> orderStatusFromClient(VatCode vatCode){
        try{
            var objectMapper = new ObjectMapper();

            var userOrderRequest = new UserOrder();
            userOrderRequest.VATCode = String.valueOf(vatCode);

            var requestData = objectMapper.writeValueAsString(userOrderRequest);

            var request = new SpomsPacketBuilder()
                    .messageCode(MessageCode.ORDER_STATUS_REQUEST)
                    .protocolVersion(ProtocolVersion.VERSION_1)
                    .data(requestData)
                    .build();

            var response = BaseUserApp.orderServerClient.SpomsCommunication().getResponse(request, MessageCode.ORDER_STATUS_RESPONSE);

            CollectionType typeReference =
                    TypeFactory.defaultInstance().constructCollectionType(List.class, OrderDTO.class);

            return objectMapper.readValue(response.data(), typeReference);

        } catch(Exception exception){
            return new ArrayList<>();
        }
    }


}
