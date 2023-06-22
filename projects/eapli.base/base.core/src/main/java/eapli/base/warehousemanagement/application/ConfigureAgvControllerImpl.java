package eapli.base.warehousemanagement.application;

import eapli.base.usermanagement.domain.BaseRoles;
import eapli.base.warehousemanagement.domain.Agv;
import eapli.base.warehousemanagement.domain.AgvStatus;
import eapli.base.warehousemanagement.domain.Warehouse;
import eapli.base.warehousemanagement.repositories.AgvRepository;

import java.util.ArrayList;

import eapli.base.infrastructure.persistence.PersistenceContext;
import eapli.framework.infrastructure.authz.application.AuthorizationService;
import eapli.framework.infrastructure.authz.application.AuthzRegistry;
import eapli.framework.io.util.Console;

public class ConfigureAgvControllerImpl implements ConfigureAgvController {

    private final AuthorizationService authz = AuthzRegistry.authorizationService();
    private final AgvRepository repository = PersistenceContext.repositories().agvs();

    /**
     * {@inheritdoc}
     */
    @Override
    public void configureAgv() {
        authz.ensureAuthenticatedUserHasAnyOf(BaseRoles.POWER_USER, BaseRoles.ADMIN);

        final Long id = this.conditionalReadId();
        final String dock = this.conditionalReadDock(id);
        final String desc = Console.readLine("Description");
        final String model = Console.readLine("Model");
        final Long autonomy = Console.readLong("Autonomy");
        //final String status = Console.readLine("Status (charging, idle, working)");

        AgvStatus readyStatus = AgvStatus.IDLE;
        try {
        Agv agv = new Agv(
                id,
                desc,
                model,
                dock,
                autonomy,
                readyStatus);

        this.repository.save(agv);

        } catch (IllegalArgumentException e) {
        System.out.println("Invalid input=> " + e);
        }

        Iterable<Agv> agvs = this.repository.findAll();

        agvs.toString();

        for (Agv agvRecord : agvs) {
            System.out.println(agvRecord.toString());
        }

    }

    public String conditionalReadDock(Long AgvId) {

        ImportWarehousePlantService iwpServ = new ImportWarehousePlantService();
        Warehouse warehousePlant = iwpServ.importWarehousePlant();
        ArrayList<String> docks = warehousePlant.listDocks();

     
        ArrayList<String> availableDocks = this.availableDocks("", docks);


        String keepAsking = "y";
        String dock = "";

        do {
            dock = Console.readLine("Dock (available: " + availableDocks.toString() + ")");

            if (!availableDocks.contains(dock)) {
                System.out.println("Invalid dock, please select another one");
            }else{
                keepAsking = "n";
            }

        } while (keepAsking.equals("y"));

        return dock;
    }

    public Long conditionalReadId() {
        String keepAsking = "n";
        Long id = (long) 0;
        do {
            id = Console.readLong("Id");
            if (this.repository.containsOfIdentity(id)) {
                keepAsking = Console.readLine("Agv with id " + id + " already exists, do you wish to replace (y/n)");
            }else{
                keepAsking = "y";
            }

        } while (!keepAsking.equals("y"));

        return id;
    }

    public ArrayList<String> availableDocks(String currId, ArrayList<String> docks) {
        Iterable<Agv> agvs = this.repository.findAll();
        ArrayList<String> usedDocks = new ArrayList<>();

        for (Agv agv : agvs) {
            if (!agv.dock().equals(currId)) {
                usedDocks.add(agv.dock());
            }
        }
        docks.removeAll(usedDocks);

        return docks;
    }

}
