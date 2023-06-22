package eapli.base.productmanagement.application;

import com.sun.istack.NotNull;
import eapli.base.productmanagement.domain.AlphaNumericCode;
import eapli.base.productmanagement.domain.ProductCategory;
import eapli.base.productmanagement.domain.ProductCategoryCode;
import eapli.base.productmanagement.repositories.ProductCategoryRepository;
import eapli.framework.application.ApplicationService;

import java.util.Optional;

@ApplicationService
public class ListProductCategoryService {
    private final ProductCategoryRepository repo;

    public ListProductCategoryService(@NotNull ProductCategoryRepository repo) {
        if(repo == null)
            throw new IllegalArgumentException("ProductCategoryRepository can't be null.");
        this.repo = repo;
    }

    public Iterable<ProductCategory> allProductCategories(){

        return repo.findAll();
    }

    public Optional<ProductCategory> findById(final String code){

        return repo.ofIdentity(ProductCategoryCode.valueOf(code));
    }

}
