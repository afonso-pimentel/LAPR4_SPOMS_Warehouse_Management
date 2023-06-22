package models;

import java.math.BigDecimal;

public class Battery {
    public BigDecimal capacity;
    public BigDecimal current;
    public BigDecimal lossRate;
    public BigDecimal chargeRate;
    public Long lowBatteryPercentage;
    public Long chargedBatteryPercentage;
    public BatteryStatus status;

    public Battery(BigDecimal capacity, BigDecimal current, BigDecimal lossRate, BigDecimal chargeRate, Long lowBatteryPercentage, Long chargedBatteryPercentage) {
        this.capacity = capacity;
        this.current = current;
        this.lossRate = lossRate;
        this.chargeRate = chargeRate;
        this.lowBatteryPercentage = lowBatteryPercentage;
        this.chargedBatteryPercentage = chargedBatteryPercentage;
        this.status = BatteryStatus.CHARGED;
    }
}
