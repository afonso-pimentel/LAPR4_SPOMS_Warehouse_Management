package eapli.base.warehousemanagement.domain;

import java.util.Arrays;

import javax.persistence.Embeddable;


@Embeddable
public class AgvDock {

    private String id;
    private Position begin;
    private Position end;
    private Position depth;
    private String accessibility;

    public AgvDock(String id, Position begin, Position end, Position depth, String accessibility) {
        String allowedAccessibi [] = {"l+", "l-", "w+", "w-"}; 
        if (id == null || id.isEmpty() )
        throw new IllegalArgumentException("Invalid agvDock id");

        if (!Arrays.toString(allowedAccessibi).contains(accessibility))
        throw new IllegalArgumentException("Invalid agvDock accessibility");

        this.id = id;
        this.begin = begin;
        this.end = end;
        this.depth = depth;
        this.accessibility = accessibility;
    }

    public AgvDock() {
    }

    

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Position getBegin() {
        return begin;
    }

    public void setBegin(Position begin) {
        this.begin = begin;
    }

    public Position getEnd() {
        return end;
    }

    public void setEnd(Position end) {
        this.end = end;
    }

    public Position getDepth() {
        return depth;
    }

    public void setDepth(Position depth) {
        this.depth = depth;
    }

    public String getAccessibility() {
        return accessibility;
    }

    public void setAccessibility(String accessibility) {
        this.accessibility = accessibility;
    }

    public String toString() {
        String str = "{\n  id: " + this.id.toString() + ",\n  begin: " + this.begin.toString() + ",\n  end" + this.end.toString() + ",\n  depth" + this.depth.toString() + ",\n  accessibility: " + this.accessibility.toString() + "\n}";
        return str;
    }
}
