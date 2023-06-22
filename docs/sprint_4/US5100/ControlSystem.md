# US5100 - Control System

# 1. Description

The Control System thread is responsible for manage the function of the AGVDigitalTwin, so it waits for an order to be assign and the sends all the routes to be calculated and waits the agv to finish picking up the products, if the battery is down he will send the agv to charge and then send it back on route.

# 2. Pseudo Code
    Waits for order to be assign [lockNewOrder]

    Sets current order

    While any product to pick:
        Calculate path to each product not picked up yet

        Calculate path back to dock

        Waits for the agv to stop moving [lockPositioningComplete]

        If down on battery:
            Sets path to the dock
            Start Charging
            Wait for battery is charged [lockBatteryCharged]

    Change status of the order

# 3. Locks for synchronization  

* lockNewOrder -> Waits for an order to be assign to the AGVDigitalTwin
* lockPositioningComplete -> Waits for the AGVDigitalTwin to complete the path
* lockBatteryCharged -> Waits for the battery to be charged