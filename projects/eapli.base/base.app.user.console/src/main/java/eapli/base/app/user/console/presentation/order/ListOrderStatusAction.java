package eapli.base.app.user.console.presentation.order;

import eapli.framework.actions.Action;

public class ListOrderStatusAction implements Action {
    @Override
    public boolean execute() {
        return new ListOrderStatusUI().show();
    }
}
