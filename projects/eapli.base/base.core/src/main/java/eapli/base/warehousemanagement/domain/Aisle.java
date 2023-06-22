package eapli.base.warehousemanagement.domain;


import java.util.ArrayList;
import java.util.Arrays;

public class Aisle {

    private int id;
    private Position begin;
    private Position end;
    private Position depth;
    private String accessibility;
    private ArrayList<Row> rows;


    public Aisle(int id, Position begin, Position end, Position depth, String accessibility, ArrayList<Row> rows) {
        String allowedAccessibi [] = {"l+", "l-", "w+", "w-"}; 
        if (id < 0 )
        throw new IllegalArgumentException("Invalid Aisle id");

        if (!Arrays.toString(allowedAccessibi).contains(accessibility))
        throw new IllegalArgumentException("Invalid Aisle accessibility");

        this.id = id;
        this.begin = begin;
        this.end = end;
        this.depth = depth;
        this.accessibility = accessibility;
        this.rows = rows;
    }

    public Aisle() {
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

    public ArrayList<Row> getRows() {
        return rows;
    }

    public void setRows(ArrayList<Row> rows) {
        this.rows = rows;
    }

    public void addRow(Row row){
        this.rows.add(row);
    }

    
    @Override
    public String toString() {

        String rowsStr = "";
        for (Row iRow : this.rows) {
            rowsStr += iRow.toString();
        }
        String str = "{\n  id: " + this.id + ",\n  begin: " + begin.toString() + ",\n  end: " + end.toString() + ",\n  depth: " + depth.toString() + ",\n  accessibility: " + this.accessibility + ",\n  rows: " + this.rows.toString() + "\n}";

        return str;
    }
}
