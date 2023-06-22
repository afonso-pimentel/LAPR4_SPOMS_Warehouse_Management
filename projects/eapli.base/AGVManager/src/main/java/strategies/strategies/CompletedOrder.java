package strategies.strategies;

import com.fasterxml.jackson.core.JsonProcessingException;
import eapli.base.infrastructure.persistence.PersistenceContext;
import eapli.base.ordersmanagement.application.ChangeOrderStatusService;
import eapli.base.ordersmanagement.domain.OrderStatus;
import enums.MessageCode;
import enums.ProtocolVersion;
import models.SpomsPacket;
import models.SpomsPacketBuilder;

public class CompletedOrder implements AgvManagerStrategy {
    @Override
    public SpomsPacket PerformStrategy(SpomsPacket packetToHandle) throws JsonProcessingException {
        Long id = Long.parseLong(packetToHandle.data());

        var orderRepo = PersistenceContext.repositories().orders();
        var orderService = new ChangeOrderStatusService(orderRepo);

        orderService.changeOrderStatus(orderRepo.ofIdentity(id).get(), OrderStatus.READY_FOR_DISPATCH);

        return new SpomsPacketBuilder()
                .messageCode(MessageCode.ACK)
                .protocolVersion(ProtocolVersion.VERSION_1)
                .data("")
                .build();
    }
}
