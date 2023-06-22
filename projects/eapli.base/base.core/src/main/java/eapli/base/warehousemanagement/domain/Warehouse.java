package eapli.base.warehousemanagement.domain;


import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

public class Warehouse {

    private String description;
    private int length;
    private int width;
    private int square;
    private String unit;
    private ArrayList<Aisle> aisles;
    private ArrayList<AgvDock> agvDocks;

    

    

    public Warehouse(String description, int length, int width, int square, String unit, ArrayList<Aisle> aisles,
            ArrayList<AgvDock> agvDocks) {

                if (description == null || description.isEmpty() )
                throw new IllegalArgumentException("Invalid Warehouse description");

                if (length <= 0 )
                throw new IllegalArgumentException("Invalid Warehouse length");

                if (width <= 0 )
                throw new IllegalArgumentException("Invalid Warehouse width");

                if (square <= 0 )
                throw new IllegalArgumentException("Invalid Warehouse square");

                if (unit == null || unit.isEmpty())
                throw new IllegalArgumentException("Invalid Warehouse unit");

                
        this.description = description;
        this.length = length;
        this.width = width;
        this.square = square;
        this.unit = unit;
        this.aisles = aisles;
        this.agvDocks = agvDocks;
    }

    

    public Warehouse() {
    }



    public String getDescription() {
        return description;
    }



    public void setDescription(String description) {
        this.description = description;
    }



    public int getLength() {
        return length;
    }


    public int getNumSqrLength() {
        return length/square;
    }



    public void setLength(int length) {
        this.length = length;
    }



    public int getWidth() {
        return width;
    }

    public int getNumSqrWidth() {
        return width/square;
    }


    public void setWidth(int width) {
        this.width = width;
    }



    public int getSquare() {
        return square;
    }



    public void setSquare(int square) {
        this.square = square;
    }



    public String getUnit() {
        return unit;
    }



    public void setUnit(String unit) {
        this.unit = unit;
    }



    public ArrayList<Aisle> getAisles() {
        return aisles;
    }



    public void setAisles(ArrayList<Aisle> aisles) {
        this.aisles = aisles;
    }



    public ArrayList<AgvDock> getAgvDocks() {
        return agvDocks;
    }



    public void setAgvDocks(ArrayList<AgvDock> agvDocks) {
        this.agvDocks = agvDocks;
    }

    public void addAisle(Aisle aisle){
        this.aisles.add(aisle);
    }

    public void addAgvDock(AgvDock agvDock){
        this.agvDocks.add(agvDock);
    }


    @Override
    public String toString() {
        return "Warehouse {  " +
                "\n  description: " + this.description +
                ", \n  length: " + this.length +
                ", \n  width: " + this.width +
                ", \n  square: " + this.square +
                ", \n  unit: " + this.unit +
                ", \n  aisles: " + this.aisles.toString() +
                ", \n  agvDocks: " + this.agvDocks.toString() +
                "\n}";
    }

    public ArrayList<String> listDocks(){
        ArrayList<String> arrDocks = new ArrayList<String>();

        for( AgvDock agvDock : this.agvDocks ) {
            arrDocks.add(agvDock.getId());
         }
         return arrDocks;
    }
}
