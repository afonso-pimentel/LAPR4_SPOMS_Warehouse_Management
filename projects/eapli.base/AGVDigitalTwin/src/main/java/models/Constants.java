package models;

import java.math.BigDecimal;

public class Constants {
    public static final Long lowPercentage = 20L;
    public static final Long chargedPercentage = 95L;
    public static final BigDecimal lossRate = BigDecimal.valueOf(0.2);
    public static final BigDecimal chargeRate = BigDecimal.valueOf(10);
    public static final int fullSpeed = 2000;
    public static final int halfSpeed = 3000;
    public static final int updateStatus = 300;
    public static final int updateObstacles = 200;
    public static final int batteryTick = 1000;
}
