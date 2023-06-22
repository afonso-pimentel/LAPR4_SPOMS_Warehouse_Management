package eapli.base.warehousemanagement.domain;

public class Row {

    private int id;
    private Position begin;
    private Position end;
    private int shelves;

    public Row(final int id, final Position begin, final Position end, final int shelves) {
        if (id <= 0)
        throw new IllegalArgumentException("Invalid Row id");

        if (shelves < 0)
        throw new IllegalArgumentException("Invalid Row shelve");

        this.id = id;
        this.begin = begin;
        this.end = end;
        this.shelves = shelves;
    }

    public Row() {
    }

    

    public int getId() {
        return id;
    }

    public void setId(int id) {
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

    public int getShelves() {
        return shelves;
    }

    public void setShelves(int shelves) {
        this.shelves = shelves;
    }

    public String toString() {
        String str = "{\n  id: " + this.id + ",\n  begin: " + this.begin.toString() + ",\n  end: " + this.end.toString() + ",\n  shelves: " + this.shelves + "\n}";
        return str;
    }
}
