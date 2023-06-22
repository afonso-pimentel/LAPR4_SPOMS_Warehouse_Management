# US5100 - Simulation Engine

# 1. Description

The Simulation Engine thread is responsible for mapping the world, for that he ask the AGVManager for the latest positions of all AGVDigitalTwins.

# 2. Pseudo Code
    
    Asks AGVManager for the last positions

    Stores the positions
    
    It asks every second

# 3. Locks for synchronization  

This thread does not need locks because it asks for data periodically.