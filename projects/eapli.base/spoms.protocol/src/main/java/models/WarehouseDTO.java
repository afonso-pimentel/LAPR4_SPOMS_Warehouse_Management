package models;

import eapli.base.warehousemanagement.domain.Position;

import java.util.List;

public class WarehouseDTO {
    public List<AisleDTO> aisles;
    public List<DockDTO> docks;
    public int length;
    public int width;
}
