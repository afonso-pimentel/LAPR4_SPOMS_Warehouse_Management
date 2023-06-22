package system;

import com.fasterxml.jackson.databind.ObjectMapper;
import communication.SpomsCommunication;
import eapli.base.warehousemanagement.domain.AgvStatus;
import enums.MessageCode;
import enums.ProtocolVersion;
import models.AgvDataResponse;
import models.Constants;
import models.SharedInformation;
import models.SpomsPacketBuilder;

import java.io.IOException;
import java.util.Arrays;
import java.util.concurrent.TimeoutException;

public class SimulationEngine extends Thread {
    SharedInformation info;
    SpomsCommunication communication;

    public SimulationEngine(SharedInformation info, SpomsCommunication communication){
        this.info = info;
        this.communication = communication;
    }

    /**
     * execute simulation engine routine
     */
    @Override
    public void run(){
        WelcomeMessage();
        ObjectMapper objectMapper = new ObjectMapper();

        while (info.currentStatus() != AgvStatus.SHUTTING_DOWN){
            try {
                var response = communication.getResponse( new SpomsPacketBuilder()
                        .messageCode(MessageCode.ALL_AGV_STATUS_REPORT_REQUEST)
                        .protocolVersion(ProtocolVersion.VERSION_1)
                        .data("")
                        .build(), MessageCode.ALL_AGV_STATUS_REPORT_RESPONSE);
                if(!response.data().isEmpty())
                    info.updateAgvPositions(Arrays.asList(objectMapper.readValue(response.data(), AgvDataResponse[].class)));

                sleep(Constants.updateObstacles); //Time between Updates;
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (TimeoutException e) {
                e.printStackTrace();
            } catch (Exception e){
               // e.printStackTrace();
            }
        }

        ShutDownMessage();
    }

    private void ShutDownMessage()
    {
        System.out.println("\nSimulation Engine ShutDown");
    }

    private void WelcomeMessage()
    {
        System.out.println("\nSimulation Engine Started");
    }
}
