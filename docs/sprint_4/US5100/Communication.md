# US5100 - Communication

# 1. Description

The communication thread is responsible to update the AGVManager with the last state of the DigitalTwin.

# 2. Pseudo Code
    Get Current AgvDigitalTwin Info from Shared Memory

    Send Information To AgvManager

    Interval of 500ms between updates
    
# 3. Locks for synchronization  

This thread does not need locks because it sends the data periodically.
