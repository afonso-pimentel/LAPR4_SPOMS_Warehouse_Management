package algorithms;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Stack;

import models.Accessibility;
import models.PositionDTO;

public class BFS {
    private static final int[][] DIRECTIONS = { { 0, 1 }, { 1, 0 }, { 0, -1 }, { -1, 0 } };
    private static boolean[][] visited;

    /**
     * Create path from src position and dest position taking into account the
     * bounds and obstacles of the worldmap
     * 
     * @param world 2D array of worldmap
     * @param src   source PositionDTO
     * @param dest  destination PositionDTO
     * @return Stack<PositionDTO> of created path
     */
    public static Stack<PositionDTO> createPath(int[][] world, PositionDTO src, PositionDTO dest) {

        // // uncomment to view world map
        // for (int i = 0; i < world.length; i++) {
        // for (int j = 0; j < world[0].length; j++) {
        // System.out.print(String.format("%2s", world[i][j]));
        // }
        // System.out.println("");
        // }

        visited = new boolean[world.length][world[0].length];

        // decrement 1 to coordinates
        src.lSquare -= 1;
        src.wSquare -= 1;
        dest.lSquare -= 1;
        dest.wSquare -= 1;

        LinkedList<Coordinate> nextToVisit = new LinkedList<>();
        Coordinate start = new Coordinate(src.wSquare.intValue(), src.lSquare.intValue());
        nextToVisit.add(start);

        try {

            while (!nextToVisit.isEmpty()) {
                Coordinate cur = nextToVisit.remove();

                if (!isValidLocation(world, cur.getRow(), cur.getCol()) || isExplored(cur.getRow(), cur.getCol())) {
                    continue;
                }

                // if position is an obstacle
                if (world[cur.getRow()][cur.getCol()] == 1 || ((world[cur.getRow()][cur.getCol()] == 2) && !isDestination(dest, cur.getRow(), cur.getCol()) && !isDock(world, cur))) {
                    setVisited(cur.getRow(), cur.getCol());
                    continue;
                }

                if (isDestination(dest, cur.getRow(), cur.getCol())) {
                    return backtrackPath(cur);
                }

                for (int[] direction : DIRECTIONS) {
                    Coordinate coordinate = new Coordinate(cur.getRow() + direction[0], cur.getCol() + direction[1], cur);

                    // only allow entry into dock from the correct side
                    if (isValidLocation(world, coordinate.getRow(), coordinate.getCol()) && world[coordinate.getRow()][coordinate.getCol()] == 2 && isDestination(dest, coordinate.getRow(), coordinate.getCol()) && dest.access != null) {

                        Accessibility accessibility = dest.access;
                        int[] dockEntrance = BFS.accessibilityToDirection(accessibility);


                        if (Arrays.equals(direction, dockEntrance)) {
                            nextToVisit.add(coordinate);
                            setVisited(cur.getRow(), cur.getCol());
                        } else {
                            setVisited(cur.getRow(), cur.getCol());
                        }

                    } else {
                        nextToVisit.add(coordinate);
                        setVisited(cur.getRow(), cur.getCol());
                    }

                }
            }
        }
        catch(Exception e) {
            e.printStackTrace();
        }

        Stack<PositionDTO> emptyStack = new Stack<PositionDTO>();
        return emptyStack;
    }

    /**
     * Verify if coordinates translate to a dock in the world map
     * @param world 2D array of int
     * @param cur Coordinate position to check
     * @return boolean
     */
    private static boolean isDock(int[][] world, Coordinate cur){

        boolean result = false; 

        if (world[cur.getRow()][cur.getCol()] == 2) {
            result = true;
        }

        return result;
    }

    /**
     * Translate accessibility to array of int in order to use as direction
     * @param access Accessibility property to check
     * @return 1D array of int
     */
    private static int[] accessibilityToDirection(Accessibility access) {

        int[] direction = new int[2];
                // right{ 0, 1 }, down{ 1, 0 }, left{ 0, -1 }, up{ -1, 0 } };

        switch (access) {
            case L_PLUS:
                direction[1] = -1; // left
                break;
            case L_LESS:
                direction[1] = 1; // right
                break;
            case W_PLUS:
                direction[0] = -1; // up
                break;
            case W_LESS:
                direction[0] = 1; // down
                break;

            default:
                break;
        }

        return direction;

    }

    /**
     * Create a queue of PositionDTO of the coordinates saved while finding the path
     * 
     * @param cur Coordinate chain
     * @return Queue<PositionDTO> path
     */
    private static Stack<PositionDTO> backtrackPath(Coordinate cur) {

        Stack<PositionDTO> path = new Stack<PositionDTO>();

        Coordinate iter = cur;

        Boolean isLast = true;

        while (iter != null) {

            // creates position but also increments 1 to conform to worldmap
            PositionDTO position = new PositionDTO(Long.valueOf(iter.getCol()) + 1, Long.valueOf(iter.getRow()) + 1);

            // assign the destination flag to the last position
            if (isLast) {
                position.isDest = true;
                isLast = false;
            }

            path.add(position);
            iter = iter.parent;

        }

        // remove the first position since it is not needed
        if (!path.isEmpty()) {
            path.pop();
        }

        return path;
    }

    /**
     * validates if world square/position is traversable, that includes obstacles
     * such as docks, aisles, and other agvs
     * which are all represented by 1 in the world array
     * 
     * @param world 2D array of worldmap
     * @param row   row position of 2D array
     * @param col   column position of 2D array
     * @return boolean
     */
    private static boolean isValidLocation(int[][] world, int row, int col) {
        if (row < 0 || row >= world.length || col < 0 || col >= world[0].length) {
            return false;
        }
        return true;
    }

    /**
     * verify if it was already visited
     * 
     * @param row row position of 2D array
     * @param col column position of 2D array
     * @return boolean
     */
    public static boolean isExplored(int row, int col) {
        return visited[row][col];
    }

    /**
     * set visited array position as visited
     * 
     * @param row row position of 2D array
     * @param col column position of 2D array
     */
    public static void setVisited(int row, int col) {
        visited[row][col] = true;
    }

    /**
     * verify if the row and the col coordinates are the same as the destination
     * 
     * @param dest PositionDTO destination
     * @param row  row position of 2D array
     * @param col  column position of 2D array
     * @return boolean
     */
    public static boolean isDestination(PositionDTO dest, int row, int col) {
        return row == dest.wSquare && col == dest.lSquare;
    }

}