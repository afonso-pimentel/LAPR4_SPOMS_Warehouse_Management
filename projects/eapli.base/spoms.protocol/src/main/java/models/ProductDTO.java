package models;

public class ProductDTO {

    public String internalCode;

    public String brand;

    public String category;

    public double price;

    public String shortDescription;

    public StorageAreaDTO position;

    public PositionDTO positionToPickUp = null;
    public boolean pickedUp = false;

    @Override
    public String toString(){
        String s = "Internal Code:"+ this.internalCode+"\n"+
                    "brand:"+ this.brand+"\n"+
                    "category:"+ this.category+"\n"+
                    "price:"+ this.brand+"\n"+
                    "shortDescription:"+ this.shortDescription+"\n";
        return s;
    }
}
