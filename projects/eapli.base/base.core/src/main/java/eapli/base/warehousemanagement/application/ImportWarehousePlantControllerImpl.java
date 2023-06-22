package eapli.base.warehousemanagement.application;

import eapli.base.warehousemanagement.domain.Warehouse;

public class ImportWarehousePlantControllerImpl implements ImportWarehousePlantController {



    /**
     * {@inheritdoc}
     */
    @Override
    public void importWarehousePlant() {

        ImportWarehousePlantService iwpServ = new ImportWarehousePlantService();
        Warehouse warehousePlant = iwpServ.importWarehousePlant();

        System.out.println("The loaded warehouse plant:\n");
        System.out.println(warehousePlant.toString());
    }

}
