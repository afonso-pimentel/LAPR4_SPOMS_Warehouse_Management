package strategies.strategies;

import enums.MessageCode;
import enums.ProtocolVersion;
import models.SpomsPacket;
import models.SpomsPacketBuilder;

/**
 * Disconnect strategy
 */
public class DisconnectStrategy implements AgvManagerStrategy {
    /**
     * @inheritDoc
     *
     */
    @Override
    public SpomsPacket PerformStrategy(SpomsPacket packetToHandle) {
        return new SpomsPacketBuilder()
                .messageCode(MessageCode.ACK)
                .protocolVersion(ProtocolVersion.VERSION_1)
                .data("OK!")
                .build();
    }
}
