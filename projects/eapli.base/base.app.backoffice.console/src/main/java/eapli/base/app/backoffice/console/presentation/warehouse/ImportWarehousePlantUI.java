package eapli.base.app.backoffice.console.presentation.warehouse;

import eapli.base.warehousemanagement.application.ImportWarehousePlantControllerImpl;
import eapli.framework.io.util.Console;
import eapli.framework.presentation.console.AbstractUI;

public class ImportWarehousePlantUI extends AbstractUI {

    private final ImportWarehousePlantControllerImpl theController = new ImportWarehousePlantControllerImpl();

    @Override
    protected boolean doShow() {

        // final String filePath = Console.readLine("Input file path");

        this.theController.importWarehousePlant();

        return false;
    }

    @Override
    public String headline() {
        return "Import warehouse plant";
    }
}
