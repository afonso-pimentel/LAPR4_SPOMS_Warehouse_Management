# US5100 - Route Planner

# 1. Description

The Route Planner thread is responsible for calculate the path between two positions.

# 2. Pseudo Code
    Waits for a route to calculate [lockCalculateRoute]

    Calculates the route

    Adds all steps to the path

    Sends signal that new path is available [lockNewPath]

# 3. Locks for synchronization  

* lockCalculateRoute -> Waits for route to calculate
* lockNewPath -> Sends signal that a route as been calculated