package system;

import AGVDigitalTwinSettings.AGVSettings;
import eapli.base.warehousemanagement.domain.AgvStatus;
import models.*;

public class Sensor extends Thread {
    SharedInformation info;
    boolean executing = true;

    public Sensor(SharedInformation info){
        this.info = info;
    }

    /**
     * execute sensor routine
     */
    @Override
    public void run(){
        WelcomeMessage();

        while (executing){
            try {
                Helper.waitForEvent(LockObjects.lockStartSensor);

                System.out.println("Reading Sensors!");

                sleep(100); //simulate work

                if(info.currentStatus() == AgvStatus.SHUTTING_DOWN) {
                    ShutDownMessage();
                    return;
                }

                var step = info.peekNextPathStep();
                var obstacles = info.agvObstacles();

                var direction = Helper.getDirection(info.currentPosition(), step);

                var closePositions = Helper.getCloseSquares(info.currentPosition(), direction);

                if (obstacles.stream().filter(a -> a.id.compareTo(AGVSettings.Settings.ID) != 0).anyMatch(a -> closePositions.stream().anyMatch(c -> c.equals(a.currentPosition)) )) {
                    System.out.println("Something in the danger zone!");
                    info.throttle(ThrottleStatus.NONE);
                    Helper.raiseEvent(LockObjects.lockThrottle);
                    continue;
                }

                var semiClosePositions = Helper.getCloseSquares(closePositions.get(0), direction);

                if (obstacles.stream().filter(a -> a.id.compareTo(AGVSettings.Settings.ID) != 0).anyMatch(a -> semiClosePositions.stream().anyMatch(c -> c.equals(a.currentPosition)) )) {
                    System.out.println("Something in the warning zone!");
                    info.throttle(ThrottleStatus.HALF);
                    Helper.raiseEvent(LockObjects.lockThrottle);
                    continue;
                }
                System.out.println("No obstacles!");
                info.throttle(ThrottleStatus.FULL);
                Helper.raiseEvent(LockObjects.lockThrottle);

                if(info.currentStatus() == AgvStatus.SHUTTING_DOWN) {
                    ShutDownMessage();
                    break;
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private void ShutDownMessage()
    {
        System.out.println("\nSensor ShutDown");
        executing = false;
    }

    private void WelcomeMessage()
    {
        System.out.println("\nSensor Started");
    }
}
