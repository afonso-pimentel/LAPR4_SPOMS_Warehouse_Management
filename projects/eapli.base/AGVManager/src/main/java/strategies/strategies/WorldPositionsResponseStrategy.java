package strategies.strategies;

import com.fasterxml.jackson.databind.ObjectMapper;

import eapli.base.warehousemanagement.application.ImportWarehousePlantService;
import eapli.base.warehousemanagement.domain.AgvDock;
import eapli.base.warehousemanagement.domain.Aisle;
import eapli.base.warehousemanagement.domain.Position;
import eapli.base.warehousemanagement.domain.Row;
import eapli.base.warehousemanagement.domain.Warehouse;
import enums.MessageCode;
import enums.ProtocolVersion;
import models.Accessibility;
import models.AisleDTO;
import models.DockDTO;
import models.PositionDTO;
import models.RowDTO;
import models.SpomsPacket;
import models.SpomsPacketBuilder;
import models.WarehouseDTO;

import java.util.ArrayList;

public class WorldPositionsResponseStrategy implements AgvManagerStrategy {

    private Warehouse warehousePlant;
    private WarehouseDTO warehouseDto;

    public WorldPositionsResponseStrategy() {
        ImportWarehousePlantService iwpServ = new ImportWarehousePlantService();
        this.warehousePlant = iwpServ.importWarehousePlant();
    }

    @Override
    public SpomsPacket PerformStrategy(SpomsPacket packetToHandle) {

        var packetData = "";
        var objectMapper = new ObjectMapper();
        try {

            this.warehouseDto = new WarehouseDTO();

            int width = this.warehousePlant.getNumSqrWidth();
            int length = this.warehousePlant.getNumSqrLength();

            this.warehouseDto.width = width;
            this.warehouseDto.length = length;

            ArrayList<Aisle> aisles = warehousePlant.getAisles();

            this.warehouseAislesToDto(aisles);

            ArrayList<AgvDock> agvDocks = warehousePlant.getAgvDocks();

            this.warehouseDocksToDto(agvDocks);

            packetData = objectMapper.writeValueAsString(this.warehouseDto);

        } catch (Exception e) {
            System.out.println("Something has gone wrong! " + e);
        }

        return new SpomsPacketBuilder()
                .messageCode(MessageCode.WORLD_POSITIONS_RESPONSE)
                .protocolVersion(ProtocolVersion.VERSION_1)
                .data(packetData)
                .build();
    }

    /**
     * Converts array list of AgvDock to list of DockDTO and assign it to this.warehouseDto
     * @param docks list of AgvDock
     */
    private void warehouseDocksToDto(ArrayList<AgvDock> docks) {

        this.warehouseDto.docks = new ArrayList<>();
        // loop all docks
        for (AgvDock dock : docks) {

            DockDTO dockDto = new DockDTO();
            dockDto.id = dock.getId();
            dockDto.position = new PositionDTO();
            dockDto.position.lSquare = Long.valueOf(dock.getEnd().getlSquare());
            dockDto.position.wSquare = Long.valueOf(dock.getBegin().getwSquare());
            dockDto.position.access = stringToAccessibility(dock.getAccessibility());

            this.warehouseDto.docks.add(dockDto);
        }
    }

    /**
     * Converts array list of Aisle to list of AisleDTO and assign it to this.warehouseDto
     * @param aisles list of Aisle
     */
    private void warehouseAislesToDto(ArrayList<Aisle> aisles) {

        this.warehouseDto.aisles = new ArrayList<>();
        // loop all aisles
        for (Aisle aisle : aisles) {

            // create aisle
            AisleDTO aisleDto = new AisleDTO();
            aisleDto.id = Long.valueOf(aisle.getId());
            aisleDto.access = stringToAccessibility(aisle.getAccessibility());
            aisleDto.rows = new ArrayList<>();

            // loop all rows
            for (Row row : aisle.getRows()) {

                // create row
                RowDTO rowDto = new RowDTO();
                rowDto.id = Long.valueOf(row.getId());
                rowDto.shelves = Long.valueOf(row.getShelves());
                rowDto.squaresOcupied = new ArrayList<>();

                Position begin = row.getBegin();
                Position end = row.getEnd();
                Position depth = aisle.getDepth();

                // assign the real coordinates according to the deph value
                begin.setwSquare(begin.getwSquare() < depth.getwSquare() ? begin.getwSquare() : depth.getwSquare());
                end.setwSquare(end.getwSquare() > depth.getwSquare() ? end.getwSquare() : depth.getwSquare());

                // loop all positions
                for (int pl = begin.getlSquare(); pl <= end.getlSquare(); pl++) {

                    for (int pw = begin.getwSquare(); pw <= end.getwSquare(); pw++) {

                        PositionDTO positionDto = new PositionDTO();

                        positionDto.lSquare = Long.valueOf(pl);
                        positionDto.wSquare = Long.valueOf(pw);
                        positionDto.isAccessable = isPositionAccessable(begin, end, pl, pw, aisleDto.access);
                        positionDto.access = stringToAccessibility(aisle.getAccessibility());

                        rowDto.squaresOcupied.add(positionDto);
                    }
                }

                aisleDto.rows.add(rowDto);
            }

            this.warehouseDto.aisles.add(aisleDto);
        }

    }

    /**
     * Verify if position is accessible
     * @param begin
     * @param end
     * @param pl
     * @param pw
     * @param access
     * @return boolean
     */
    private boolean isPositionAccessable(Position begin, Position end, int pl, int pw, Accessibility access) {

        boolean result = false;
        switch (access) {
            case W_PLUS:
                if (pw == end.getwSquare()) {
                    result = true;
                }
                break;
            case W_LESS:
                if (pw == begin.getwSquare()) {
                    result = true;
                }
                break;
            case L_PLUS:
                if (pl == end.getlSquare()) {
                    result = true;
                }
                break;
            case L_LESS:
                if (pl == begin.getlSquare()) {
                    result = true;
                }
                break;

        }

        return result;
    }

    /**
     * Convert String w-, w+, l-, l+ to Accessibility Enum
     * @param str String w-, w+, l-, l+
     * @return Accessibility Enum
     */
    private Accessibility stringToAccessibility(String str) {
        switch (str) {

            case "w-":
                return Accessibility.W_LESS;
            case "l+":
                return Accessibility.L_PLUS;
            case "l-":
                return Accessibility.L_LESS;
            case "w+":
            default:
                return Accessibility.W_PLUS;
        }
    }

}
