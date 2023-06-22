package eapli.base.productmanagement.application;

import eapli.base.infrastructure.persistence.PersistenceContext;
import eapli.base.productmanagement.domain.Product;
import eapli.base.productmanagement.repositories.ProductRepository;
import eapli.base.usermanagement.domain.BaseRoles;
import eapli.framework.infrastructure.authz.application.AuthorizationService;
import eapli.framework.infrastructure.authz.application.AuthzRegistry;

public class ListProductControllerImpl implements ListProductController {

    private final AuthorizationService authz;

    private final ProductRepository repo;
    private final ListProductService productService;

    /**
     * Needed when you have another constructor
     */
    public ListProductControllerImpl()
    {
        authz = AuthzRegistry.authorizationService();
        repo = PersistenceContext.repositories().products();
        productService = new ListProductService();
    }

    /**
     * Used in tests to mock the dependencies
     * @param _authz
     * @param _repo
     */
    public ListProductControllerImpl(AuthorizationService _authz, ProductRepository _repo, ListProductService _productService) {
        this.authz = _authz;
        this.repo = _repo;
        this.productService = _productService;
    }

    public Iterable<Product> sortAndFilterProducts(String filterField, String filter, String sortField, Boolean ascending) {
        authz.ensureAuthenticatedUserHasAnyOf(BaseRoles.POWER_USER, BaseRoles.ADMIN, BaseRoles.SALES_CLERK);

        if(filterField == null && sortField == null)
            return productService.allProducts();

        return repo.filterSortActive(filterField,filter, sortField, ascending);
    }
}
