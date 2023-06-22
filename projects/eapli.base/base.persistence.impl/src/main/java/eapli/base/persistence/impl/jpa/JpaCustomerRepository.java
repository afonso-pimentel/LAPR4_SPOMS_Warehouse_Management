package eapli.base.persistence.impl.jpa;

import eapli.base.Application;
import eapli.base.customerusermanagement.domain.Customer;
import eapli.base.customerusermanagement.domain.Gender;
import eapli.base.customerusermanagement.domain.PhoneNumber;
import eapli.base.customerusermanagement.domain.VatCode;
import eapli.base.customerusermanagement.repositories.CustomerRepository;
import eapli.base.ordersmanagement.domain.Order_;
import eapli.framework.domain.repositories.TransactionalContext;
import eapli.framework.general.domain.model.EmailAddress;
import eapli.framework.infrastructure.repositories.impl.jpa.JpaAutoTxRepository;

import javax.persistence.TypedQuery;
import java.util.HashMap;
import java.util.Optional;

public class JpaCustomerRepository extends JpaAutoTxRepository<Customer, VatCode, VatCode>
        implements CustomerRepository {

    public JpaCustomerRepository(final TransactionalContext autoTx) {
        super(autoTx, "vatID");
    }

    public JpaCustomerRepository(final String puname) {
        super(puname, Application.settings().getExtendedPersistenceProperties(),
                "vatID");
    }

    @Override
    public Optional<Customer> findEquals(final VatCode vatCode, final PhoneNumber phoneNumber, final EmailAddress emailAddress) {
        //SELECT e FROM Customer e WHERE e.VATID = vatCode
        return matchOne("e.vatID="+vatCode+" OR e.phoneNumber="+phoneNumber+" OR e.email=\'"+emailAddress+"\'");
    }

    /**
     * @inheritDoc
     *
     */
    @Override
    public Optional<Customer> findByVatCode(VatCode vatCode) {
        if(vatCode == null)
            throw new IllegalArgumentException("VatCode cannot be null");

        var query = new StringBuilder();
        query.append("e.vatID = :vatCode");

        var params = new HashMap<String, Object>();
        params.put("vatCode", VatCode.valueOf(vatCode.toString()));

        return matchOne(query.toString(), params);
    }

    /**
     * @inheritDoc
     *
     */
    @Override
    public Iterable<Customer> findByAgeInterval(int startInterval, int endInterval) {
        var customQuery = new StringBuilder();

        customQuery.append("SELECT e ");
        customQuery.append("FROM Customer e ");
        customQuery.append("WHERE e IN ");
        customQuery.append("(SELECT DISTINCT o FROM Customer o WHERE YEAR(o.birthDate) BETWEEN YEAR(GETDATE()) - :endInterval AND YEAR(GETDATE()) - :startInterval)");

        final TypedQuery<Customer> query = entityManager().createQuery(customQuery.toString(), Customer.class)
                .setParameter("endInterval", endInterval)
                .setParameter("startInterval", startInterval);

        return query.getResultList();
    }

    /**
     * @inheritDoc
     *
     */
    @Override
    public Iterable<Customer> findByGender(Gender gender) {
        var customQuery = new StringBuilder();

        customQuery.append("SELECT e ");
        customQuery.append("FROM Customer e ");
        customQuery.append("WHERE e IN ");
        customQuery.append("(SELECT DISTINCT o FROM Customer o WHERE o.gender = :gender)");

        final TypedQuery<Customer> query = entityManager().createQuery(customQuery.toString(), Customer.class)
                .setParameter("gender", gender);

        return query.getResultList();
    }
}
