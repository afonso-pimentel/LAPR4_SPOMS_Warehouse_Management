package system;

import models.DockDTO;
import models.PositionDTO;
import models.SharedInformation;
import models.WarehouseDTO;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class RoutePlannerTest {

    @Test
    void getRouteNegativePosition() {

        WarehouseDTO wDto = new WarehouseDTO();
        wDto.length = 5;
        wDto.width = 5;
        wDto.docks = new ArrayList<DockDTO>();
        DockDTO dDto = new DockDTO();
        dDto.position = new PositionDTO();
        dDto.position.lSquare = 2L;
        dDto.position.wSquare = -1L;

        wDto.docks.add(dDto);

        SharedInformation si = new SharedInformation(wDto);

        assertThrows(NullPointerException.class, () -> {
            RoutePlanner rp = new RoutePlanner(si);
        });
    }

    @Test
    void getRouteOutofboundsPosition() {

        WarehouseDTO wDto = new WarehouseDTO();
        wDto.length = 5;
        wDto.width = 5;
        wDto.docks = new ArrayList<DockDTO>();
        DockDTO dDto = new DockDTO();
        dDto.position = new PositionDTO();
        dDto.position.lSquare = 2L;
        dDto.position.wSquare = 5L;

        wDto.docks.add(dDto);

        SharedInformation si = new SharedInformation(wDto);

        assertThrows(NullPointerException.class, () -> {
            RoutePlanner rp = new RoutePlanner(si);
        });
    }
}