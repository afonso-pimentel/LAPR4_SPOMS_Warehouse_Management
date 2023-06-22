package system;

import AGVDigitalTwinSettings.AGVSettings;
import eapli.base.warehousemanagement.domain.AgvStatus;
import models.*;

public class Positioning extends Thread {
    SharedInformation info;
    boolean executing = true;
    boolean stopped = false;
    public Positioning(SharedInformation info){
        this.info = info;
    }

    /**
     * execute Positioning routine
     */
    @Override
    public void run(){
        WelcomeMessage();

        while (executing){
            try {
                if (info.currentStatus() == AgvStatus.SHUTTING_DOWN){
                    ShutDownMessage();
                    break;
                }

                while (info.emptyPath()) {
                    Helper.raiseEvent(LockObjects.lockPositioningComplete);

                    Helper.waitForEvent(LockObjects.lockNewPath);

                    if (info.currentStatus() == AgvStatus.SHUTTING_DOWN) {
                        ShutDownMessage();
                        return;
                    }
                }

                if(info.battery().status == BatteryStatus.LOW_BATTERY && info.currentStatus() != AgvStatus.LOW_BATTERY) {
                    info.clearPath();
                    Helper.raiseEvent(LockObjects.lockPositioningComplete);
                    continue;
                }

                Helper.raiseEvent(LockObjects.lockStartSensor);

                Helper.waitForEvent(LockObjects.lockThrottle);

                if (info.currentStatus() == AgvStatus.SHUTTING_DOWN){
                    ShutDownMessage();
                    break;
                }

                var peekNext = info.peekNextPathStep();

                switch (info.throttle())
                {
                    case FULL:
                        sleep(AGVSettings.Settings.fullSpeed);
                        System.out.println("Moving...");
                        info.currentPosition(info.nextPathStep());
                        stopped = false;

                        break;
                    case HALF:
                        sleep(AGVSettings.Settings.halfSpeed);
                        System.out.println("Moving slowly...");
                        info.currentPosition(info.nextPathStep());
                        stopped = false;

                        break;
                    case NONE:
                        sleep(AGVSettings.Settings.halfSpeed);
                        if(!stopped) {
                            System.out.println("Waiting...");
                            stopped = true;
                        }
                        else{
                            System.out.println("Repositioning...");
                            var right = Helper.getRight(info.currentPosition(), info.getDirection());
                            //validate if he can go to right
                            if(Helper.isPositionValid(info.worldMap(),right)) {
                                info.currentPosition(right);
                                info.clearPath();
                                System.out.println("I will dodge now!");
                                Helper.raiseEvent(LockObjects.lockPositioningComplete);
                            }else
                                stopped = false;
                        }
                        break;

                    default:
                        break;

                }

                if(peekNext != null) {
                    if (peekNext.isDest)
                        info.productPicked(peekNext);
                }

            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }


    private void ShutDownMessage()
    {
        System.out.println("\nPositioning System ShutDown");
        executing = false;
    }

    private void WelcomeMessage()
    {
        System.out.println("\nPositioning System Started");
    }
}
