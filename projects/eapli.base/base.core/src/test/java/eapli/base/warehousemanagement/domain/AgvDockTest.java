package eapli.base.warehousemanagement.domain;

import eapli.base.warehousemanagement.domain.AgvDock;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.ArrayList;

import org.junit.Before;

public class AgvDockTest {

    private Position begin;
    private Position end;
    private Position depth;
    private ArrayList<Row> rows;


    @Before
    public void init() {
        this.begin = new Position(1,1);
        this.end = new Position(2,2);
        this.depth = new Position(2,2);
    }



    @Test
    public void invalidEmptyIdShouldThrowIllegalArgumentException() {

        assertThrows(IllegalArgumentException.class, () -> {
            AgvDock agvDock = new AgvDock(
                "",
                this.begin,
                this.end,
                this.depth,
                "l+"
            );
        });
    }

    @Test
    public void invalidNullIdShouldThrowIllegalArgumentException() {

        assertThrows(IllegalArgumentException.class, () -> {
            AgvDock agvDock = new AgvDock(
                null,
                this.begin,
                this.end,
                this.depth,
                "l+"
            );
        });
    }

    @Test
    public void invalidAccessibilityShouldThrowIllegalArgumentException() {

        assertThrows(IllegalArgumentException.class, () -> {
            AgvDock agvDock = new AgvDock(
                "B1",
                this.begin,
                this.end,
                this.depth,
                "ll+"
            );
        });
    }







}