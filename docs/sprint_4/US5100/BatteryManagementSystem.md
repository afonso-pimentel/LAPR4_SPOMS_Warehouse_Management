# US5100 - Battery Management System

# 1. Description

The Battery Management System thread is responsible to charge the battery and periodically decrement the charge at a certain rate, and alert that the AGVDigitalTwin is low on battery.

# 2. Pseudo Code
    Get battery Info from Shared Memory

    If is charging:
        if already charged:
            Change status
            Raise the lockBatteryCharged Event
        if not charged:
            Add to the current battery
    if not charging:
        Decrement the current battery

    if battery is 0:
        Change Status to SHUTTING_DOWN

    Update the battery

    Interval of 1000ms between updates (constant batteryTick)
    
# 3. Locks for synchronization  

The lockBatteryCharged is used to notify the Control System that the AGVDigitalTwin is charged.