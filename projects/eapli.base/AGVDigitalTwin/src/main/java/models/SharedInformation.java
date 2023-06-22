package models;

import AGVDigitalTwinSettings.AGVSettings;
import eapli.base.warehousemanagement.domain.AgvStatus;
import org.apache.commons.lang3.SerializationUtils;

import java.util.*;
import java.util.stream.Collectors;

public class SharedInformation {

    private WarehouseDTO world;
    private List<AgvDataResponse> agvPositions;
    private Queue<PositionDTO> currentPath;
    private Queue<OrderTaskAssignment> ordersToComplete;
    private ThrottleStatus throttleStatus ;
    private OrderTaskAssignment currentOrder;
    private Queue<Route> routesToCalculate;

    public SharedInformation(WarehouseDTO world){
        this.world = world;
        this.agvPositions = new ArrayList<>();
        this.currentPath = new LinkedList<>();
        this.ordersToComplete = new LinkedList<>();
        this.routesToCalculate = new LinkedList<>();
        throttleStatus = ThrottleStatus.NONE;
    }

    public synchronized WarehouseDTO worldMap() { return world; }

    public synchronized AgvStatus currentStatus() { return AGVSettings.Settings.currentStatus; }
    public synchronized void currentStatus(AgvStatus st) {
        AGVSettings.Settings.currentStatus =
                AGVSettings.Settings.currentStatus == AgvStatus.SHUTTING_DOWN
                        ? AGVSettings.Settings.currentStatus
                        : st;
    }

    public synchronized PositionDTO currentPosition() { return SerializationUtils.clone(AGVSettings.Settings.currentPosition); }
    public synchronized void currentPosition(PositionDTO p) { AGVSettings.Settings.currentPosition = SerializationUtils.clone(p); }
    public synchronized PositionDTO dockPosition() { return SerializationUtils.clone(AGVSettings.Settings.dock.position); }

    public synchronized Battery battery() { return AGVSettings.Settings.battery; }
    public synchronized void battery(Battery bt) { AGVSettings.Settings.battery = bt; }

    public synchronized List<AgvDataResponse> agvObstacles() { return this.agvPositions; }
    public synchronized void updateAgvPositions(List<AgvDataResponse> agvP) { this.agvPositions = agvP; }


    public synchronized Direction getDirection() {
        return Helper.getDirection(AGVSettings.Settings.currentPosition, this.currentPath.peek());
    }
    public synchronized boolean emptyPath() { return this.currentPath.isEmpty(); }
    public synchronized void clearPath() { this.currentPath.clear(); }
    public synchronized void addNewStepToPath(PositionDTO p) { this.currentPath.add(SerializationUtils.clone(p)); }
    public synchronized PositionDTO nextPathStep() { return this.currentPath.poll(); }
    public synchronized PositionDTO peekNextPathStep() { return this.currentPath.peek(); }

    public synchronized ThrottleStatus throttle(){return this.throttleStatus;}
    public synchronized void throttle(ThrottleStatus throttle){ this.throttleStatus = throttle;}

    public synchronized void addNewOrder(OrderTaskAssignment p) { this.ordersToComplete.add(p); }
    public synchronized OrderTaskAssignment nextOrder() { return this.ordersToComplete.poll(); }
    public synchronized boolean emptyOrders() { return this.ordersToComplete.isEmpty(); }


    public synchronized OrderTaskAssignment currentOrder(){return this.currentOrder;}
    public synchronized void currentOrder(OrderTaskAssignment currentOrder){ this.currentOrder = currentOrder;}
    public synchronized void productPicked(PositionDTO prodPosition){
        if (this.currentOrder == null)
            return;

        var prods = this.currentOrder.products.stream().filter(productDTO -> prodPosition.equals(productDTO.positionToPickUp)).collect(Collectors.toList());
        for (var prod :
                prods) {
            System.out.println("Product Pick UP!");
            prod.pickedUp = true;
        }
    }

    public synchronized boolean emptyRoute() { return this.routesToCalculate.isEmpty(); }
    public synchronized void addNewRoute(Route r) { this.routesToCalculate.add(SerializationUtils.clone(r)); }
    public synchronized Route nextRoute() { return this.routesToCalculate.poll(); }
}
