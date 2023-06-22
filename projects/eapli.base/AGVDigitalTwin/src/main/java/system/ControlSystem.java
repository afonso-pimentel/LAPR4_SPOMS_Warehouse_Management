package system;

import communication.SpomsCommunication;
import eapli.base.warehousemanagement.domain.AgvStatus;
import enums.MessageCode;
import enums.ProtocolVersion;
import models.*;
import org.apache.commons.lang3.SerializationUtils;

import java.io.IOException;
import java.util.concurrent.TimeoutException;
import java.util.stream.Collectors;

public class ControlSystem  extends Thread {
    SharedInformation info;
    SpomsCommunication communication;
    boolean executing = true;

    public ControlSystem(SharedInformation info, SpomsCommunication spomsCommunication){
        this.info = info;
        this.communication = spomsCommunication;
    }

    /**
     * execute battery control system routine
     */
    @Override
    public void run(){

        WelcomeMessage();

        while (executing){
            while (info.emptyOrders()){
                Helper.waitForEvent(LockObjects.lockNewOrder);
                if(info.currentStatus() == AgvStatus.SHUTTING_DOWN) {
                    ShutDownMessage();
                    return;
                }
            }

            var order = info.nextOrder();
            info.currentOrder(order);

            System.out.println("Picking up order #" + order.OrderToDo());

            grabOrder();

            if(info.currentStatus() == AgvStatus.SHUTTING_DOWN) {
                ShutDownMessage();
                break;
            }

            try {
                communication.getResponse(new SpomsPacketBuilder()
                        .messageCode(MessageCode.ORDER_COMPLETED)
                        .protocolVersion(ProtocolVersion.VERSION_1)
                        .data(String.valueOf(order.OrderToDo()))
                        .build(), MessageCode.ACK);
            } catch (IOException e) {
            } catch (TimeoutException e) {
            }

            info.currentOrder(null);

            //When he is in dock he is always charging
            info.currentStatus(AgvStatus.IDLE);
            var bt = info.battery();
            bt.status = BatteryStatus.CHARGING;
            info.battery(bt);
        }
    }

    /**
     * execute all paths required to get all products in order
     */
    private void grabOrder(){
        while (info.currentOrder().products.stream().anyMatch(productDTO -> !productDTO.pickedUp)){
            info.currentStatus(AgvStatus.WORKING);
            var order = info.currentOrder();
            info.clearPath();
            PositionDTO start = info.currentPosition();
            for ( var prod : order.products.stream().filter(productDTO -> !productDTO.pickedUp).collect(Collectors.toList())) {
                var route = new Route();
                route.start = SerializationUtils.clone(start);
                route.end = getProductPosition(prod.position.aisle, prod.position.row, prod.position.shelf);
                if(route.end.equals(route.start))
                {
                    prod.positionToPickUp = SerializationUtils.clone(route.start);
                    continue;
                }

                prod.positionToPickUp = SerializationUtils.clone(route.end);

                info.addNewRoute(SerializationUtils.clone(route));
                Helper.raiseEvent(LockObjects.lockCalculateRoute);
                start = route.end;
            }

            //route to dock
            var route = new Route();
            route.start = SerializationUtils.clone(start);
            route.end = info.dockPosition();
            info.addNewRoute(SerializationUtils.clone(route));
            Helper.raiseEvent(LockObjects.lockCalculateRoute);

            if(info.currentStatus() == AgvStatus.SHUTTING_DOWN)
                return;

            Helper.waitForEvent(LockObjects.lockPositioningComplete);

            if(info.currentStatus() == AgvStatus.SHUTTING_DOWN)
                return;

            manageAgvBattery();
        }
    }

    /**
     * Get product position by aisleid, rowid, and shelfid
     * @param aisleId Long 
     * @param rowId Long
     * @param shelfId Long
     * @return PositionDTO of worldmap square
     */
    private PositionDTO getProductPosition(Long aisleId, Long rowId, Long shelfId){

        var aisles = info.worldMap();

        var aisle = aisles.aisles.stream().filter(aisleDTO -> aisleDTO.id == aisleId).findFirst().get();

        var row = aisle.rows.stream().filter(rowDTO -> rowDTO.id == rowId).findFirst().get();

        var productPosition = SerializationUtils.clone(row.squaresOcupied.stream().filter(positionDTO -> positionDTO.isAccessable).findFirst().get());

        while (row.squaresOcupied.stream().anyMatch(positionDTO -> positionDTO.equals(productPosition))){
            switch (aisle.access){
                case L_LESS:
                    productPosition.lSquare--;
                    break;
                case L_PLUS:
                    productPosition.lSquare++;
                    break;
                case W_LESS:
                    productPosition.wSquare--;
                    break;
                case W_PLUS:
                    productPosition.wSquare++;
                    break;
                default:
                    break;
            }
        }
        productPosition.access = null;
        return productPosition;
    }

    /**
     * Execute low battery routine, where it plans a route back to the dock when on low battery
     */
    private void manageAgvBattery() {
        if(info.battery().status != BatteryStatus.LOW_BATTERY)
            return;

        info.clearPath();
        info.currentStatus(AgvStatus.LOW_BATTERY);

        var route = new Route();
        route.start = info.currentPosition();
        route.end = info.dockPosition();
        info.addNewRoute(route);
        Helper.raiseEvent(LockObjects.lockCalculateRoute);

        Helper.waitForEvent(LockObjects.lockPositioningComplete);

        var lastStatus = info.currentStatus();

        info.currentStatus(AgvStatus.CHARGING);
        var bt = info.battery();
        bt.status = BatteryStatus.CHARGING;
        info.battery(bt);

        Helper.waitForEvent(LockObjects.lockBatteryCharged);

        info.currentStatus(lastStatus);
    }

    private void ShutDownMessage()
    {
        System.out.println("\nControl System ShutDown");
        executing = false;
    }

    private void WelcomeMessage()
    {
        System.out.println("\nControl System Started");
    }
}
