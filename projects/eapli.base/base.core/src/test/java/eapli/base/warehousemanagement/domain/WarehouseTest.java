package eapli.base.warehousemanagement.domain;

import eapli.base.warehousemanagement.domain.Warehouse;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.ArrayList;

import org.junit.Before;

public class WarehouseTest {

    private ArrayList<Aisle> aisles;
    private ArrayList<AgvDock> agvDocks;

    @Before
    public void init() {
        this.aisles = new ArrayList<Aisle>();
        this.agvDocks = new ArrayList<AgvDock>();
    }

    @Test
    public void invalidEmptyDescriptionShouldThrowIllegalArgumentException() {

        assertThrows(IllegalArgumentException.class, () -> {
            Warehouse warehouse = new Warehouse(
                    "",
                    1,
                    1,
                    1,
                    "cm",
                    this.aisles,
                    this.agvDocks);
        });
    }

    @Test
    public void invalidNullDescriptionShouldThrowIllegalArgumentException() {

        assertThrows(IllegalArgumentException.class, () -> {
            Warehouse warehouse = new Warehouse(
                    null,
                    1,
                    1,
                    1,
                    "cm",
                    this.aisles,
                    this.agvDocks);
        });
    }

    @Test
    public void invalidNegativeLengthShouldThrowIllegalArgumentException() {

        assertThrows(IllegalArgumentException.class, () -> {
            Warehouse warehouse = new Warehouse(
                    "description",
                    -1,
                    1,
                    1,
                    "cm",
                    this.aisles,
                    this.agvDocks);
        });
    }

    @Test
    public void invalidNegativeWidthShouldThrowIllegalArgumentException() {

        assertThrows(IllegalArgumentException.class, () -> {
            Warehouse warehouse = new Warehouse(
                    "description",
                    1,
                    -1,
                    1,
                    "cm",
                    this.aisles,
                    this.agvDocks);
        });
    }

    @Test
    public void invalidNegativeSquareShouldThrowIllegalArgumentException() {

        assertThrows(IllegalArgumentException.class, () -> {
            Warehouse warehouse = new Warehouse(
                    "description",
                    1,
                    1,
                    -1,
                    "cm",
                    this.aisles,
                    this.agvDocks);
        });
    }

    @Test
    public void invalidEmptyUnitShouldThrowIllegalArgumentException() {

        assertThrows(IllegalArgumentException.class, () -> {
            Warehouse warehouse = new Warehouse(
                    "description",
                    1,
                    1,
                    1,
                    "",
                    this.aisles,
                    this.agvDocks);
        });
    }

    
    @Test
    public void invalidNullUnitShouldThrowIllegalArgumentException() {

        assertThrows(IllegalArgumentException.class, () -> {
            Warehouse warehouse = new Warehouse(
                    "description",
                    1,
                    1,
                    1,
                    null,
                    this.aisles,
                    this.agvDocks);
        });
    }


}