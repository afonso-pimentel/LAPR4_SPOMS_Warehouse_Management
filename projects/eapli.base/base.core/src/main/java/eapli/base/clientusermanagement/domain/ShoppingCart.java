package eapli.base.clientusermanagement.domain;

import eapli.framework.general.domain.model.Money;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


public class ShoppingCart {
    private List<ShoppingCartLine> shoppingCartLine;
    private Money total;

    public static class Cache {
        private static List<ShoppingCartLine> currentShoppingCart = new ArrayList<>();
        public static void addProduct(ShoppingCartLine product){
            currentShoppingCart.add(product);
        }
        public static List<ShoppingCartLine> ShoppingCartList(){return currentShoppingCart;}
    }

    protected ShoppingCart(List<ShoppingCartLine> shoppingCartLine) {
        this.shoppingCartLine = shoppingCartLine;
        this.total.add(Money.euros(0));
        for (ShoppingCartLine spl: this.shoppingCartLine) {
            this.total.add(Money.euros(spl.getPrice()).multiply(spl.getQuantity()));
        }
    }

    protected ShoppingCart(){
        //for ORM
    }


    public static ShoppingCart valueOf(List<ShoppingCartLine> shoppingCartLine, double total){
        return new ShoppingCart(shoppingCartLine);
    }

    public ShoppingCart clone(){
        return new ShoppingCart(this.shoppingCartLine);
    }

    @Override
    public String toString(){
        String s = "";
        for (ShoppingCartLine spl: this.shoppingCartLine) {
            s += spl.toString() + "\n";
        }
        s+= "; Total: "+ this.total+"$\n";
        return s;
    }
    @Override
    public int hashCode(){
        return total.hashCode();
    }

//    @Override
//    public int compareTo(ShoppingCart o){
//        if(this.shoppingCartLine.equals(o.shoppingCartLine) && Objects.equals(this.total, o.total))
//            return 1;
//        else
//            return 0;
//    }
}
