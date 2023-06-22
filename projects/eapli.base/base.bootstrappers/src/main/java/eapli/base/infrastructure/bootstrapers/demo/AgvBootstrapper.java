package eapli.base.infrastructure.bootstrapers.demo;

import eapli.base.infrastructure.persistence.PersistenceContext;
import eapli.base.warehousemanagement.domain.Agv;
import eapli.base.warehousemanagement.domain.AgvStatus;
import eapli.base.warehousemanagement.repositories.AgvRepository;
import eapli.framework.actions.Action;
import eapli.framework.domain.repositories.ConcurrencyException;
import eapli.framework.domain.repositories.IntegrityViolationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class AgvBootstrapper implements Action {
    private static final Logger LOGGER = LoggerFactory.getLogger(
            AgvBootstrapper.class);

    private final AgvRepository agvRepository;

    public AgvBootstrapper(){
        agvRepository = PersistenceContext.repositories().agvs();
    }

    @Override
    public boolean execute() {

        registerAgv(1l,"D6","smallRobot","A32B5",100l,AgvStatus.SHUTTING_DOWN);
        registerAgv(12l,"D2","smallRobot","A32B5",100l,AgvStatus.SHUTTING_DOWN);
        registerAgv(13l,"D3","smallRobot","A32B5",100l,AgvStatus.SHUTTING_DOWN);

        return true;
    }

    private void registerAgv(final Long id,final String dock,final String desc,final String model,final Long autonomy,final AgvStatus status){
        try {
            agvRepository.save(new Agv(id, desc, model, dock, autonomy, status));

        } catch (final ConcurrencyException | IntegrityViolationException e) {
            LOGGER.warn("Assuming {} already exists (activate trace log for details)", id);
            LOGGER.trace("Assuming existing record", e);
        }
    }
}