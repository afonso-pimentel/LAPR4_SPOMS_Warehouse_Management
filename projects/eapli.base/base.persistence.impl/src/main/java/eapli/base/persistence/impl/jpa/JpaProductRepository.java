package eapli.base.persistence.impl.jpa;

import eapli.base.Application;
import eapli.base.clientusermanagement.domain.ClientUser;
import eapli.base.clientusermanagement.domain.MecanographicNumber;
import eapli.base.clientusermanagement.repositories.ClientUserRepository;
import eapli.base.productmanagement.domain.AlphaNumericCode;
import eapli.base.productmanagement.domain.Product;
import eapli.base.productmanagement.repositories.ProductRepository;
import eapli.framework.domain.repositories.TransactionalContext;
import eapli.framework.infrastructure.authz.domain.model.Username;
import eapli.framework.infrastructure.repositories.impl.jpa.JpaAutoTxRepository;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.Optional;

public class JpaProductRepository
        extends JpaAutoTxRepository<Product, AlphaNumericCode, AlphaNumericCode>
        implements ProductRepository {

    public JpaProductRepository(final TransactionalContext autoTx) {
        super(autoTx, "internalCode");
    }

    public JpaProductRepository(final String puname) {
        super(puname, Application.settings().getExtendedPersistenceProperties(),
                "internalCode");
    }

    @Override
    public Iterable<Product> findAllActive() {
        //SELECT e FROM Product e WHERE e.active=true
        return match("e.active=true");
    }

    @Override
    public Iterable<Product> filterSortActive(String filterField, String filter, String sortField, Boolean asc) {

        String where = "e.active=true ";

        if(filterField != null)
        {
            filterField = filterField.toLowerCase();
            filterField = filterField.replace(" ","");

            where += "AND e." + filterField + " like '%" + filter + "%' ";
        }

        if(sortField != null)
        {
            sortField = sortField.toLowerCase();
            sortField = sortField.replace(" ","");

            where += "ORDER BY " + sortField + (asc ? " ASC" : " DESC");
        }

        return match(where);
    }

    /**
     * @inheritDoc
     */
    @Override
    public Optional<Product> find(AlphaNumericCode productCode) {
        var parameters = new HashMap<String, Object>();
        parameters.put("internalCode", productCode);

        return matchOne("e.internalCode = :internalCode", parameters);
    }
}
