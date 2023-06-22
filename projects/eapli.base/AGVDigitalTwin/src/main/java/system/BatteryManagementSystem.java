package system;

import eapli.base.warehousemanagement.domain.AgvStatus;
import models.*;
import java.math.BigDecimal;

public class BatteryManagementSystem extends Thread {
    SharedInformation info;

    public BatteryManagementSystem(SharedInformation info){
        this.info = info;
    }

    /**
     *  execute battery management routine
     */
    @Override
    public void run(){
        WelcomeMessage();

        while (info.currentStatus() != AgvStatus.SHUTTING_DOWN){
            try {
                var bt = info.battery();
                if(bt.status == BatteryStatus.CHARGING)
                {
                    if (bt.current.compareTo(Helper.longPercentageToBigDecimalOf(bt.chargedBatteryPercentage, bt.capacity)) >= 0) {
                        bt.status = BatteryStatus.CHARGED;
                        Helper.raiseEvent(LockObjects.lockBatteryCharged);
                    }
                    else {
                        if (bt.current.add(bt.chargeRate).compareTo(bt.capacity) > 0)
                            bt.current = bt.capacity;
                        else
                            bt.current = bt.current.add(bt.chargeRate);
                    }
                }else if(info.currentStatus() != AgvStatus.IDLE)
                {
                    if (bt.current.subtract(bt.lossRate).compareTo(BigDecimal.valueOf(0)) < 0)
                        bt.current = BigDecimal.valueOf(0);
                    else
                        bt.current = bt.current.subtract(bt.lossRate);
                }

                if(bt.current.compareTo(Helper.longPercentageToBigDecimalOf(bt.lowBatteryPercentage, bt.capacity)) < 0
                        && info.currentStatus() != AgvStatus.CHARGING
                        && bt.status == BatteryStatus.CHARGED)
                    bt.status = BatteryStatus.LOW_BATTERY;

                if (bt.current.compareTo(new BigDecimal(0)) <= 0)
                    info.currentStatus(AgvStatus.SHUTTING_DOWN);

                info.battery(bt);

                sleep(Constants.batteryTick);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        ShutDownMessage();
    }

    private void ShutDownMessage()
    {
        System.out.println("\nBattery management system ShutDown");
    }

    private void WelcomeMessage()
    {
        System.out.println("\nBattery management system Started");
    }
}
