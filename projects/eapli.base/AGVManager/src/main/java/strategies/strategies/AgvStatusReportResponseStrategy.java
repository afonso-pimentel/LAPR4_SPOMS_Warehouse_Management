package strategies.strategies;

import com.fasterxml.jackson.databind.ObjectMapper;
import eapli.base.infrastructure.persistence.PersistenceContext;
import eapli.base.warehousemanagement.application.AgvService;
import eapli.base.warehousemanagement.domain.Agv;
import eapli.base.warehousemanagement.repositories.AgvRepository;
import enums.MessageCode;
import enums.ProtocolVersion;
import models.AgvDataResponse;
import models.SpomsPacket;
import models.SpomsPacketBuilder;

public class AgvStatusReportResponseStrategy implements AgvManagerStrategy{
    @Override
    public SpomsPacket PerformStrategy(SpomsPacket packetToHandle) {

        try {
            ObjectMapper mapper = new ObjectMapper();
            AgvDataResponse agvData = mapper.readValue(packetToHandle.data(),
                                        AgvDataResponse.class);

            if (agvData.id != null)
            {
                if(!agvData.id.equals(1L)) {
                    Long agvId = agvData.id;
                    AgvRepository agvRepository = PersistenceContext.repositories().agvs();

                    if (!agvRepository.containsOfIdentity(agvId))
                        return new SpomsPacketBuilder()
                                .messageCode(MessageCode.ACK)
                                .protocolVersion(ProtocolVersion.VERSION_1)
                                .data("")
                                .build();

                    Agv agv = agvRepository.ofIdentity(agvId).get();

                    AgvService agvService = new AgvService(agvRepository);

                    agvService.changeAgvStatus(agv, agvData.status);
                }
                // add or update single agv data to "cache"
                AGVManagerCaching.AgvStatusCache.StatusCache.updateAgvStatus(agvData);
            }

        }catch (Exception e)
        {
            System.out.println("Something has gone wrong!2 " + e);
        }

        return new SpomsPacketBuilder()
                .messageCode(MessageCode.ACK)
                .protocolVersion(ProtocolVersion.VERSION_1)
                .data("")
                .build();

    }
}

