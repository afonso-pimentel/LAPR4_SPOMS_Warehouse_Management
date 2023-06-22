package eapli.base.warehousemanagement.application;

import eapli.base.infrastructure.persistence.PersistenceContext;
import eapli.base.usermanagement.domain.BaseRoles;
import eapli.base.warehousemanagement.domain.Agv;
import eapli.base.warehousemanagement.domain.AgvStatus;
import eapli.base.warehousemanagement.repositories.AgvRepository;
import eapli.framework.infrastructure.authz.application.AuthorizationService;
import eapli.framework.infrastructure.authz.application.AuthzRegistry;

public class AgvService {
    private final AuthorizationService authz;
    private final AgvRepository agvRepository;
    private final boolean notInternal;

    public AgvService(AuthorizationService _authz, AgvRepository _agvRepository){
        authz = _authz;
        agvRepository = _agvRepository;
        notInternal =true;
    }

    public AgvService(AgvRepository _agvRepository){
        authz = null;
        agvRepository = _agvRepository;
        notInternal = false;
    }

    public Iterable<Agv> availableAgvs(){
        return agvRepository.findAgvByStatus(AgvStatus.IDLE);
    }



    public void changeAgvStatus(Agv agv, AgvStatus agvStatus){
        if(notInternal)
            this.authz.ensureAuthenticatedUserHasAnyOf(BaseRoles.POWER_USER, BaseRoles.ADMIN, BaseRoles.WAREHOUSE_EMPLOYEE);

        if(agvRepository.contains(agv)) {
            agv.changeAgvStatus(agvStatus);
            agvRepository.save(agv);
        }else{
            throw new IllegalArgumentException("This order is not registered.");
        }
    }
}
