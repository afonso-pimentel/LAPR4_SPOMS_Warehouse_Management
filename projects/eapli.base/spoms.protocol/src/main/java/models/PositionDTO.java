package models;

import java.io.Serializable;
import java.util.Objects;

public class PositionDTO implements Serializable {
    public Long lSquare;
    public Long wSquare;
    public boolean isAccessable;
    public Accessibility access = null;
    public boolean isDest;


    public PositionDTO(){
        this.isDest = false;
    };

    public PositionDTO(Long lSquare, Long wSquare) {
        this.lSquare = lSquare;
        this.wSquare = wSquare;
        this.isDest = false;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass())
            return false;
        PositionDTO that = (PositionDTO) o;
        return lSquare.equals(that.lSquare) && wSquare.equals(that.wSquare);
    }

    @Override
    public int hashCode() {
        return Objects.hash(lSquare, wSquare);
    }
}
