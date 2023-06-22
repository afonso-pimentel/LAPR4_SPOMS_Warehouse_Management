package eapli.base.persistence.impl.jpa;

import eapli.base.Application;
import eapli.base.warehousemanagement.domain.Agv;
import eapli.base.warehousemanagement.domain.AgvStatus;
import eapli.base.warehousemanagement.repositories.AgvRepository;
import eapli.framework.domain.repositories.TransactionalContext;
import eapli.framework.infrastructure.repositories.impl.jpa.JpaAutoTxRepository;



public class JpaAgvRepository
        extends JpaAutoTxRepository<Agv, Long, Long>
        implements AgvRepository {

    public JpaAgvRepository(final TransactionalContext autoTx) {
        super(autoTx, "id");
    }

    public JpaAgvRepository(final String puname) {
        super(puname, Application.settings().getExtendedPersistenceProperties(),
                "id");
    }


    @Override
    public Iterable<Agv> findAgvByStatus(AgvStatus agvStatus) {
        return match("e.status = " + agvStatus.ordinal());
    }
}
