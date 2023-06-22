package eapli.base.productmanagement.application;

import eapli.base.infrastructure.persistence.PersistenceContext;
import eapli.base.productmanagement.domain.Product;
import eapli.base.productmanagement.repositories.ProductRepository;
import eapli.base.usermanagement.domain.BaseRoles;
import eapli.framework.infrastructure.authz.application.AuthorizationService;
import eapli.framework.infrastructure.authz.application.AuthzRegistry;

public class ListProductService {
    private final AuthorizationService authz;
    private final ProductRepository productRepository;

    /**
     * Needed when you have another constructor
     */
    public ListProductService()
    {
        authz = AuthzRegistry.authorizationService();
        productRepository = PersistenceContext.repositories().products();
    }

    /**
     * Used in tests to mock the dependencies
     * @param _authz
     * @param _repo
     */
    public ListProductService(AuthorizationService _authz, ProductRepository _repo) {
        this.authz = _authz;
        this.productRepository = _repo;
    }

    public Iterable<Product> allProducts() {
        authz.ensureAuthenticatedUserHasAnyOf(BaseRoles.POWER_USER, BaseRoles.ADMIN, BaseRoles.SALES_CLERK);

        return productRepository.findAllActive();
    }
}
