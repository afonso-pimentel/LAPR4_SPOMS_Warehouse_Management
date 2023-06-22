package strategies.strategies;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.type.CollectionType;
import com.fasterxml.jackson.databind.type.TypeFactory;
import enums.MessageCode;
import enums.ProtocolVersion;
import models.AgvDataResponse;
import models.SpomsPacket;
import models.SpomsPacketBuilder;

import java.util.List;

public class AllAGVStatusReportRequestStrategy implements AgvManagerStrategy {

    @Override
    public SpomsPacket PerformStrategy(SpomsPacket packetToHandle) {
        var jsonString = "";

        try {
            ObjectMapper mapper = new ObjectMapper();

            CollectionType typeReference =
                  TypeFactory.defaultInstance().constructCollectionType(List.class, AgvDataResponse.class);


            jsonString = mapper.writeValueAsString(AGVManagerCaching.AgvStatusCache.StatusCache.getAllAgvStatus());

        } catch (Exception e) {
            System.out.println("Something has gone wrong!3 " + e);
        }
        return new SpomsPacketBuilder()
                .messageCode(MessageCode.ALL_AGV_STATUS_REPORT_RESPONSE)
                .protocolVersion(ProtocolVersion.VERSION_1)
                .data(jsonString)
                .build();
    }

}
