package eapli.base.warehousemanagement.domain;

import eapli.base.warehousemanagement.domain.Aisle;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.ArrayList;

import org.junit.Before;

public class AisleTest {

    private Position begin;
    private Position end;
    private Position depth;
    private ArrayList<Row> rows;


    @Before
    public void init() {
        this.begin = new Position(1,1);
        this.end = new Position(2,2);
        this.depth = new Position(2,2);
        this.rows = new ArrayList<Row>();
    }



    @Test
    public void invalidNegativeIdShouldThrowIllegalArgumentException() {

        assertThrows(IllegalArgumentException.class, () -> {
            Aisle aisle = new Aisle(
                -1,
                this.begin,
                this.end,
                this.depth,
                "l+",
                this.rows
            );
        });
    }

    @Test
    public void invalidAccessibilityShouldThrowIllegalArgumentException() {

        assertThrows(IllegalArgumentException.class, () -> {
            Aisle aisle = new Aisle(
                1,
                this.begin,
                this.end,
                this.depth,
                "ll+",
                this.rows
            );
        });
    }







}