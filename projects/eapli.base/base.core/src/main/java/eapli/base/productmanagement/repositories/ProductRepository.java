package eapli.base.productmanagement.repositories;

import eapli.base.productmanagement.domain.AlphaNumericCode;
import eapli.base.productmanagement.domain.Product;
import eapli.framework.domain.repositories.DomainRepository;

import java.util.Optional;

public interface ProductRepository extends DomainRepository<AlphaNumericCode, Product> {

    /**
     * List All active products
     * @return
     */
    Iterable<Product> findAllActive();

    /**
     * filter and sort Products
     * @return
     */
    Iterable<Product> filterSortActive(String filterField, String filter, String sortField, Boolean asc);

    /**
     * Tries to find the product based on the product code
     * @param productCode ProductCode
     * @return Product
     */
    Optional<Product> find(AlphaNumericCode productCode);
}
