package eapli.base.warehousemanagement.domain;

import eapli.base.warehousemanagement.domain.Row;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.Before;

public class RowTest {

    private Position begin;
    private Position end;


    @Before
    public void init() {
        this.begin = new Position(1,1);
        this.end = new Position(2,2);
    }



    @Test
    public void invalidNegativeIdShouldThrowIllegalArgumentException() {

        assertThrows(IllegalArgumentException.class, () -> {
            Row row = new Row(
                -1,
                this.begin,
                this.end,
                2
            );
        });
    }

    @Test
    public void invalidNegativeShelveShouldThrowIllegalArgumentException() {

        assertThrows(IllegalArgumentException.class, () -> {
            Row row = new Row(
                1,
                this.begin,
                this.end,
                -2
            );
        });
    }





}