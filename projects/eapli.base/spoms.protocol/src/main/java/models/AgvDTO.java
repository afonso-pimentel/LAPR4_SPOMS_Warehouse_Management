package models;

import java.math.BigDecimal;

public class AgvDTO extends ErrorResponse{
   public String dock;
   public BigDecimal batteryCapacity;


    public AgvDTO(String message) {
        super(true, message);
    }

    public AgvDTO() {
        super(false, "");
    }
}
