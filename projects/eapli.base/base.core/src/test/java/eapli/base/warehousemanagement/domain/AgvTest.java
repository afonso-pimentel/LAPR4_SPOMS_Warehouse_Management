package eapli.base.warehousemanagement.domain;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.Before;

public class AgvTest {

    private Long id;
    private String description;
    private String model;
    private String dockId;
    private Long autonomy;
    private AgvStatus status;



    @Before
    public void init() {
        this.id = 1l;
        this.description = "desc";
        this.model = "model";
        this.dockId = "D1";
        this.autonomy = 120l;
        this.status =  AgvStatus.IDLE;
    }



    @Test
    public void invalidNegativeIdShouldThrowIllegalArgumentException() {

        assertThrows(IllegalArgumentException.class, () -> {
            Agv agv = new Agv(
                (long)-1,
                this.description,
                this.model,
                this.dockId,
                this.autonomy,
                this.status
            );
        });
    }

    @Test
    public void invalidnullIdShouldThrowIllegalArgumentException() {

        assertThrows(IllegalArgumentException.class, () -> {
            Agv agv = new Agv(
                null,
                this.description,
                this.model,
                this.dockId,
                this.autonomy,
                this.status
            );
        });
    }

    
    @Test
    public void invalidNullDescriptionShouldThrowIllegalArgumentException() {

        assertThrows(IllegalArgumentException.class, () -> {
            Agv agv = new Agv(
                this.id,
                null,
                this.model,
                this.dockId,
                this.autonomy,
                this.status
            );
        });
    }

    @Test
    public void invalidEmptyDescriptionShouldThrowIllegalArgumentException() {

        assertThrows(IllegalArgumentException.class, () -> {
            Agv agv = new Agv(
                this.id,
                "",
                this.model,
                this.dockId,
                this.autonomy,
                this.status
            );
        });
    }

    @Test
    public void invalidLengthDescriptionShouldThrowIllegalArgumentException() {
        System.out.println();

        assertThrows(IllegalArgumentException.class, () -> {
            Agv agv = new Agv(
                this.id,
                "aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa",
                this.model,
                this.dockId,
                this.autonomy,
                this.status
            );
        });
    }

    @Test
    public void invalidNullModelShouldThrowIllegalArgumentException() {
        System.out.println();

        assertThrows(IllegalArgumentException.class, () -> {
            Agv agv = new Agv(
                this.id,
                this.description,
                null,
                this.dockId,
                this.autonomy,
                this.status
            );
        });
    }

    @Test
    public void invalidEmptyModelShouldThrowIllegalArgumentException() {
        System.out.println();

        assertThrows(IllegalArgumentException.class, () -> {
            Agv agv = new Agv(
                this.id,
                this.description,
                "",
                this.dockId,
                this.autonomy,
                this.status
            );
        });
    }

    @Test
    public void invalidLengthModelShouldThrowIllegalArgumentException() {
        System.out.println();

        assertThrows(IllegalArgumentException.class, () -> {
            Agv agv = new Agv(
                this.id,
                this.description,
                "aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa",
                this.dockId,
                this.autonomy,
                this.status
            );
        });
    }

    @Test
    public void invalidnullDockidShouldThrowIllegalArgumentException() {
        System.out.println();

        assertThrows(IllegalArgumentException.class, () -> {
            Agv agv = new Agv(
                this.id,
                this.description,
                this.model,
                null,
                this.autonomy,
                this.status
            );
        });
    }

    @Test
    public void invalidNullAutonomyShouldThrowIllegalArgumentException() {
        System.out.println();

        assertThrows(IllegalArgumentException.class, () -> {
            Agv agv = new Agv(
                this.id,
                this.description,
                this.model,
                this.dockId,
                null,
                this.status
            );
        });
    }

    @Test
    public void invalidNegativeAutonomyShouldThrowIllegalArgumentException() {
        System.out.println();

        assertThrows(IllegalArgumentException.class, () -> {
            Agv agv = new Agv(
                this.id,
                this.description,
                this.model,
                this.dockId,
                -1l,
                this.status
            );
        });
    }

    @Test
    public void invalidNullStatusShouldThrowIllegalArgumentException() {
        System.out.println();

        assertThrows(IllegalArgumentException.class, () -> {
            Agv agv = new Agv(
                this.id,
                this.description,
                this.model,
                this.dockId,
                this.autonomy,
                null
            );
        });
    }

   /* @Test
    public void invalidStatusShouldThrowIllegalArgumentException() {
        System.out.println();

        assertThrows(IllegalArgumentException.class, () -> {
            Agv agv = new Agv(
                this.id,
                this.description,
                this.model,
                this.dockId,
                this.autonomy,
                "test123"
            );
        });
    }*/







}