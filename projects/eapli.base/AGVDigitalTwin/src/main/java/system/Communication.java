package system;

import AGVDigitalTwinSettings.AGVSettings;
import com.fasterxml.jackson.databind.ObjectMapper;
import communication.SpomsCommunication;
import eapli.base.warehousemanagement.domain.AgvStatus;
import enums.MessageCode;
import enums.ProtocolVersion;
import models.*;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

public class Communication extends Thread {
    SharedInformation info;
    SpomsCommunication communication;

    public Communication(SharedInformation info, SpomsCommunication communication){
        this.info = info;
        this.communication = communication;
    }

    /**
     * execute battery comunication routine
     */
    @Override
    public void run(){
        WelcomeMessage();
        ObjectMapper objectMapper = new ObjectMapper();

        while (info.currentStatus() != AgvStatus.SHUTTING_DOWN){
            try {
                communication.getResponse(new SpomsPacketBuilder()
                    .messageCode(MessageCode.AGV_STATUS_REPORT_RESPONSE)
                    .protocolVersion(ProtocolVersion.VERSION_1)
                    .data(objectMapper.writeValueAsString(Helper.getDigitalTwinStatus(info)))
                    .build(), MessageCode.ACK);

                sleep(Constants.updateStatus); //Time between Updates;
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (TimeoutException e) {
                e.printStackTrace();
            } catch (Exception e){
                //e.printStackTrace();
            }
        }

        try {
            communication.getResponse(new SpomsPacketBuilder()
                    .messageCode(MessageCode.AGV_STATUS_REPORT_RESPONSE)
                    .protocolVersion(ProtocolVersion.VERSION_1)
                    .data(objectMapper.writeValueAsString(Helper.getDigitalTwinStatus(info)))
                    .build(), MessageCode.ACK);
        } catch (Exception e) {
        }

        ShutDownMessage();
    }

    private void ShutDownMessage()
    {
        System.out.println("\nCommunications ShutDown");
    }

    private void WelcomeMessage()
    {
        System.out.println("\nCommunications Started");
    }
}
