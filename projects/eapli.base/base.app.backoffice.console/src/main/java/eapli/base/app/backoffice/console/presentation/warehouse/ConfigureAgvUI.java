package eapli.base.app.backoffice.console.presentation.warehouse;

import eapli.base.warehousemanagement.application.ConfigureAgvControllerImpl;
import eapli.framework.io.util.Console;
import eapli.framework.presentation.console.AbstractUI;

public class ConfigureAgvUI extends AbstractUI {

    private final ConfigureAgvControllerImpl theController = new ConfigureAgvControllerImpl();

    @Override
    protected boolean doShow() {

        this.theController.configureAgv();

        return false;
    }

    @Override
    public String headline() {
        return "Configure Agv";
    }
}
