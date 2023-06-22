package models;

import eapli.base.ordersmanagement.domain.OrderLine;
import eapli.base.ordersmanagement.domain.OrderStatus;

import java.util.Date;
import java.util.Set;


public class OrderDTO {
    public Long orderID;
    public OrderStatus orderStatus;
    public Date orderDate;

    @Override
    public String toString(){
        String s = "ID: "+ this.orderID+
                "| Status: "+ this.orderStatus+
                "| Date: "+ this.orderDate+"\n";
        return s;
    }
}


