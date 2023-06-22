package algorithms;

import models.PositionDTO;
import org.junit.jupiter.api.Test;
import system.RoutePlanner;

import java.util.Stack;

import static org.junit.jupiter.api.Assertions.*;

class BFSTest {

    @Test
    void isDestinationEqual() {

        PositionDTO dest = new PositionDTO(1L, 2L);
        int col = 1;
        int row = 2;

        assertTrue(BFS.isDestination(dest, row, col));
    }

    @Test
    void isDestinationNotEqual() {

        PositionDTO dest = new PositionDTO(1L, 2L);
        int col = 1;
        int row = 3;

        assertFalse(BFS.isDestination(dest, row, col));
    }


    @Test
    void createPathOutofBounds() {

        int[][] world = new int[5][5];
        PositionDTO src = new PositionDTO(6L, 1L);
        PositionDTO dest = new PositionDTO(1L, 1L);

        Stack<PositionDTO> path =  BFS.createPath(world, src, dest);

        assertTrue(path.isEmpty());
    }

    @Test
    void createPathSuccess() {

        int[][] world = new int[5][5];
        PositionDTO src = new PositionDTO(1L, 1L);
        PositionDTO dest = new PositionDTO(2L, 2L);

        Stack<PositionDTO> path =  BFS.createPath(world, src, dest);

        assertTrue(!path.isEmpty());
    }

    @Test
    void createPathInvalidStartPositionIsAisle() {

        int[][] world = new int[5][5];
        world[0][0] = 1;
        PositionDTO src = new PositionDTO(1L, 1L);
        PositionDTO dest = new PositionDTO(2L, 2L);

        Stack<PositionDTO> path =  BFS.createPath(world, src, dest);

        assertTrue(path.isEmpty());
    }

    @Test
    void createPathInvalidDestPositionIsAisle() {

        int[][] world = new int[5][5];
        world[1][1] = 1;
        PositionDTO src = new PositionDTO(1L, 1L);
        PositionDTO dest = new PositionDTO(2L, 2L);

        Stack<PositionDTO> path =  BFS.createPath(world, src, dest);

        assertTrue(path.isEmpty());
    }

    @Test
    void createPathValidStartPositionIsDock() {

        int[][] world = new int[5][5];
        world[0][0] = 2;
        PositionDTO src = new PositionDTO(1L, 1L);
        PositionDTO dest = new PositionDTO(2L, 2L);

        Stack<PositionDTO> path =  BFS.createPath(world, src, dest);

        assertTrue(!path.isEmpty());
    }

    @Test
    void createPathValidDestPositionIsDock() {

        int[][] world = new int[5][5];
        world[1][1] = 2;
        PositionDTO src = new PositionDTO(1L, 1L);
        PositionDTO dest = new PositionDTO(2L, 2L);

        Stack<PositionDTO> path =  BFS.createPath(world, src, dest);

        assertTrue(!path.isEmpty());
    }

    @Test
    void createPathNoPossiblePath() {

        int[][] world = new int[5][5];
        world[0][2] = 1;
        world[1][2] = 1;
        world[2][2] = 1;
        world[3][2] = 1;
        world[4][2] = 1;
        PositionDTO src = new PositionDTO(1L, 1L);
        PositionDTO dest = new PositionDTO(3L, 3L);

        Stack<PositionDTO> path =  BFS.createPath(world, src, dest);

        assertTrue(path.isEmpty());
    }


}