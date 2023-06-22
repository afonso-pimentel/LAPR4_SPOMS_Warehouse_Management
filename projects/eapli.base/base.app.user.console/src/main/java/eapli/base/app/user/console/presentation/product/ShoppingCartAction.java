package eapli.base.app.user.console.presentation.product;

import eapli.framework.actions.Action;

public class ShoppingCartAction implements Action {

    @Override
    public boolean execute() {
        return new ShoppingCartUI().show();
    }
}
