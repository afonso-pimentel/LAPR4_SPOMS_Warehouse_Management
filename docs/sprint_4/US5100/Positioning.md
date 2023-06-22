# US5100 - Positioning

# 1. Description

The Positioning thread is responsible for moving the AGVDigitalTwin at a certain speed, and dodge other obstacles.

# 2. Pseudo Code
    Waits for new path to be calculated [lockNewPath]

    If battery is low:
        Send signal to control system to go to dock [lockPositioningComplete]

    Starts sensor [lockStartSensor]

    Waits for throttle to be calculated [lockThrottle]
    
    Moves at a certain speed

    If is stopped
        Moves the AGVDigitalTwin to the right

    If position is destination
        Set product as picked up

# 3. Locks for synchronization  

* lockNewPath -> Waits for an new route to be calculated
* lockPositioningComplete -> Sends that the agv has stopped
* lockStartSensor -> Starts the sensor so he can check for collisions
* lockThrottle -> Waits for sensor to calculate the throttle