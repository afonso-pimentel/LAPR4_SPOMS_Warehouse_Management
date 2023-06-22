package eapli.base.app.backoffice.console.presentation.warehouse;

import eapli.framework.actions.Action;

public class ConfigureAgvAction implements Action {


    @Override
    public boolean execute() {
        return new ConfigureAgvUI().show();
    }
}
