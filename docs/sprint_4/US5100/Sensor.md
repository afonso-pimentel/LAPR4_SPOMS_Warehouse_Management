# US5100 - Sensor

# 1. Description

The Sensor thread is responsible for calculate the throttle power according to the surroundings.

# 2. Pseudo Code
    Waits for a need to start [lockStartSensor]

    Gets next step and obstacles

    Calculates the direction

    Get the danger zone for the current position (red color on image 1)

    If any AGVDigitalTwin there
        Set Throttle to NONE
    
    Get the warning zone for the current position (yellow color on image 1)

    If any AGVDigitalTwin there
        Set Throttle to HALF

    If there is no AGVDigitalTwin
        Set Throttle to FULL

    Raise the event that throttle was calculated [lockThrottle]

Image 1:

![Collisions](Collisions.png)

# 3. Locks for synchronization  

* lockStartSensor -> Waits the need to start working
* lockThrottle -> Sends signal that throttle was calculated