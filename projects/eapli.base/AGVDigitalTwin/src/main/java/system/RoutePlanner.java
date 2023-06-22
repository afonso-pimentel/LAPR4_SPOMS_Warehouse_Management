package system;

import algorithms.BFS;
import eapli.base.warehousemanagement.domain.AgvStatus;
import models.*;

import java.util.List;
import java.util.Stack;

public class RoutePlanner extends Thread {
    SharedInformation info;
    boolean executing = true;
    int[][] world;
    public RoutePlanner(SharedInformation info) {
        this.info = info;
        world = this.positionsToArray();
    }

    /**
     * execute route planner routine
     */
    @Override
    public void run() {
        WelcomeMessage();

        while (executing){
            while(info.emptyRoute()){
                Helper.waitForEvent(LockObjects.lockCalculateRoute);
                if(info.currentStatus() == AgvStatus.SHUTTING_DOWN) {
                    ShutDownMessage();
                    return;
                }
            }

            if(info.currentStatus() == AgvStatus.SHUTTING_DOWN) {
                ShutDownMessage();
                return;
            }

            var route = info.nextRoute();

            //System.out.println("New Route to Plan Received from {" + route.start.lSquare + "|" + route.start.wSquare + "} to {" + route.end.lSquare + "|" + route.end.wSquare + "}");

            var path = getRoute(route.start, route.end);

            if (path.isEmpty())
                System.out.println("Could not calculate route.");

            while (!path.isEmpty()) {
                PositionDTO nextStep = path.pop();
                if (nextStep != null) {
                    //System.out.println("{row = " + nextStep.wSquare + ", col = " + nextStep.lSquare + "}");
                    info.addNewStepToPath(nextStep);
                }
            }
            Helper.raiseEvent(LockObjects.lockNewPath);
        }
    }

    private void ShutDownMessage() {
        System.out.println("\nRoute Planer ShutDown");
        executing = false;
    }

    private void WelcomeMessage() {
        System.out.println("\nRoute Planer Started");
    }

    /**
     * Get route given current position and destination position
     * 
     * @param currPosition PositionDTO object of current/starting position
     * @param destPosition PositionDTO object of destination position
     * @return Stack<PositionDTO> Linked list of PositionDTO that comprise the path
     */
    public Stack<PositionDTO> getRoute(PositionDTO currPosition, PositionDTO destPosition) {

        Stack<PositionDTO> path = BFS.createPath(world, currPosition, destPosition);

        return path;
    }

    /**
     * Converts the lists of AisleDTO and DockDTO objects to a single 2D array that
     * maps all the obstacles in the worldMap
     * in array, an obstacle is represented by 1, and clear space by 0
     * 
     * @return 2D array of obstacles
     */
    public int[][] positionsToArray() {

        var warehouseDto = info.worldMap();

        int arr[][] = new int[warehouseDto.width][warehouseDto.length];

        List<AisleDTO> aisles = warehouseDto.aisles;

        int wCol = 0;
        int wRow = 0;

        try {
            // fill the world array with squares of aisles
        for (AisleDTO aisle : aisles) {

            for (RowDTO row : aisle.rows) {

                for (PositionDTO position : row.squaresOcupied) {

                    wCol = position.lSquare.intValue() - 1;
                    wRow = position.wSquare.intValue() - 1;
                    arr[wRow][wCol] = 1;
                }
            }
        }

        List<DockDTO> docks = warehouseDto.docks;

        // fill the world array with squares of docks
        for (DockDTO dock : docks) {

            wCol = dock.position.lSquare.intValue() - 1;
            wRow = dock.position.wSquare.intValue() - 1;

            arr[wRow][wCol] = 2;
        }
            
        }catch(ArrayIndexOutOfBoundsException e) {
             e.printStackTrace();
        }
        

        return arr;
    }
}
