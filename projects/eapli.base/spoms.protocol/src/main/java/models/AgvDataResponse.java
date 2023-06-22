package models;

import eapli.base.warehousemanagement.domain.AgvStatus;

import java.math.BigDecimal;

public class AgvDataResponse {
    public Long id;
    public AgvStatus status;
    public PositionDTO currentPosition;
    public BigDecimal currentBattery;
}
