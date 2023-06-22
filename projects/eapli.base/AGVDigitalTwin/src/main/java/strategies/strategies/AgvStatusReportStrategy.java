package strategies.strategies;

import AGVDigitalTwinSettings.AGVSettings;
import com.fasterxml.jackson.databind.ObjectMapper;
import enums.MessageCode;
import enums.ProtocolVersion;
import models.AgvDataResponse;
import models.SpomsPacket;
import models.SpomsPacketBuilder;

public class AgvStatusReportStrategy implements SpomsStrategy {
    @Override
    public SpomsPacket PerformStrategy(SpomsPacket packetToHandle) {
       /* try {

            var agvData = new AgvDataResponse();
            agvData.id = AGVSettings.Settings.ID.toString();
            agvData.x = "9";
            agvData.y = "5";
            agvData.status = "working";
            var jsonString = "";


            if (agvData.id.equals("1"))
            {
                jsonString = Simulation.agvData(agvData.id);
            }
            else
            {
                agvData.id = null;
                agvData.x = "0";
                agvData.y = "0";
                agvData.status = "0";
                ObjectMapper mapper = new ObjectMapper();
                jsonString = mapper.writeValueAsString(agvData);
            }

            return new SpomsPacketBuilder()
                    .messageCode(MessageCode.AGV_STATUS_REPORT_RESPONSE)
                    .protocolVersion(ProtocolVersion.VERSION_1)
                    .data(jsonString)
                    .build();

        }catch (Exception e)
        {
            System.out.println("Something has gone wrong!1 " + e);
        }*/

        return new SpomsPacketBuilder()
        .messageCode(MessageCode.AGV_STATUS_REPORT_RESPONSE)
        .protocolVersion(ProtocolVersion.VERSION_1)
        .data("")
        .build();
    }
}
