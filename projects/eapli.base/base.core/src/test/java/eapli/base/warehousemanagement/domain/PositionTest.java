package eapli.base.warehousemanagement.domain;

import eapli.base.warehousemanagement.domain.Position;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertThrows;

public class PositionTest {

    @Test
    public void invalidNegativePositionShouldThrowIllegalArgumentException() {

        assertThrows(IllegalArgumentException.class, () -> {
            Position aisleBegin = new Position(-2, 2);
        });
    }

    @Test
    public void invalidZeroPositionShouldThrowIllegalArgumentException() {

        assertThrows(IllegalArgumentException.class, () -> {
            Position aisleBegin = new Position(0, 2);
        });
    }

}