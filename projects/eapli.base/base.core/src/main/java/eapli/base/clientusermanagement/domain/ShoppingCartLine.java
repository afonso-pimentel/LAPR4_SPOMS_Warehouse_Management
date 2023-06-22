package eapli.base.clientusermanagement.domain;

import eapli.framework.domain.model.ValueObject;

import java.util.Objects;


public class ShoppingCartLine implements ValueObject, Comparable<ShoppingCartLine>{
    private String internalCode;
    private String brand;
    private String category;
    private double price;
    private String shortDescription;
    private int quantity;

    public String getInternalCode() {
        return internalCode;
    }

    public String getBrand() {
        return brand;
    }

    public String getCategory() {
        return category;
    }

    public double getPrice() {
        return price;
    }

    public String getShortDescription() {
        return shortDescription;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public ShoppingCartLine(String internalCode, String brand, String category, double price, String shortDescription, int quantity) {
        this.internalCode = internalCode;
        this.brand = brand;
        this.category = category;
        this.price = price;
        this.shortDescription = shortDescription;
        this.quantity = quantity;
    }

    protected ShoppingCartLine(){
        //for ORM
    }

    public static ShoppingCartLine valueOf(String internalCode,String brand,String category,double price,String shortDescription, int quantity){
        return new ShoppingCartLine(internalCode,brand,category,price,shortDescription,quantity);
    }

    public ShoppingCartLine clone(){
        return new ShoppingCartLine(this.internalCode,this.brand,this.category,this.price,this.shortDescription, this.quantity);
    }

    @Override
    public String toString(){
        return "Product : "+ this.internalCode +"\n"+
                "Brand: "+this.brand +"\n"+
                "Category: "+this.category +"\n"+
                "Price: "+this.price +"\n"+
                "Short Description: "+this.shortDescription +"\n"+
                "Quantity : "+ quantity +"\n";
    }
    @Override
    public int hashCode(){
        return internalCode.hashCode();
    }


    @Override
    public int compareTo(ShoppingCartLine o){
        if(this.internalCode.equals(o.internalCode) && Objects.equals(this.quantity, o.quantity))
            return 1;
        else
            return 0;
    }
}
