package strategies.strategies;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import eapli.base.infrastructure.persistence.PersistenceContext;
import eapli.base.warehousemanagement.domain.Agv;
import eapli.base.warehousemanagement.repositories.AgvRepository;
import enums.MessageCode;
import enums.ProtocolVersion;
import models.AgvDTO;
import models.SpomsPacket;
import models.SpomsPacketBuilder;

import java.math.BigDecimal;

public class AgvInfoStrategy implements AgvManagerStrategy {
    @Override
    public SpomsPacket PerformStrategy(SpomsPacket packetToHandle) throws JsonProcessingException {

        Long id = Long.parseLong(packetToHandle.data());
        AgvRepository agvRepository = PersistenceContext.repositories().agvs();

        ObjectMapper objectMapper = new ObjectMapper();

        if(!agvRepository.containsOfIdentity(id))
            return new SpomsPacketBuilder()
                    .messageCode(MessageCode.AGV_INFO_RESPONSE)
                    .protocolVersion(ProtocolVersion.VERSION_1)
                    .data(
                            objectMapper.writeValueAsString(
                                    new AgvDTO("There is no agv with id " + id)
                            )
                    ).build();

        Agv agv = agvRepository.ofIdentity(id).get();

        var agvInfo = new AgvDTO();
        agvInfo.dock = agv.dock();
        agvInfo.batteryCapacity = new BigDecimal(agv.batteryAutonomy());

        return new SpomsPacketBuilder()
                .messageCode(MessageCode.AGV_INFO_RESPONSE)
                .protocolVersion(ProtocolVersion.VERSION_1)
                .data(objectMapper.writeValueAsString(agvInfo))
                .build();
    }
}
