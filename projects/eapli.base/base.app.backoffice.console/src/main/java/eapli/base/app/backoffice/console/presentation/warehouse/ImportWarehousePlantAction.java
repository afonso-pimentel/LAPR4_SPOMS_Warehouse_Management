package eapli.base.app.backoffice.console.presentation.warehouse;

import eapli.framework.actions.Action;

public class ImportWarehousePlantAction implements Action {


    @Override
    public boolean execute() {
        return new ImportWarehousePlantUI().show();
    }
}
