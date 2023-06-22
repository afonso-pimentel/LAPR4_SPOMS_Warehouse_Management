package eapli.base.app.user.console.presentation.product;

import eapli.base.clientusermanagement.domain.ShoppingCart;
import eapli.base.clientusermanagement.domain.ShoppingCartLine;
import eapli.framework.presentation.console.AbstractUI;

public class ListShoppingCartUI extends AbstractUI {
    protected boolean doShow() {
        if(ShoppingCart.Cache.ShoppingCartList().size() == 0){
            System.out.println("Carrinho vazio");
        }else{
            double total = 0;
            for (ShoppingCartLine sc: ShoppingCart.Cache.ShoppingCartList()) {
                System.out.println(sc.toString());
                total += (sc.getPrice()*sc.getQuantity());
            }
            System.out.printf("Total: "+total+"\n");
        }

        return true;
    }

    @Override
    public String headline() {
        return "ShoppingCart";
    }
}
