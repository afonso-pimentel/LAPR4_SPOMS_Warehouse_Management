package eapli.base.app.user.console.presentation.product;


import eapli.framework.actions.Action;

public class ListShoppingCartAction implements Action {
    @Override
    public boolean execute() {
        return new ListShoppingCartUI().show();
    }
}
