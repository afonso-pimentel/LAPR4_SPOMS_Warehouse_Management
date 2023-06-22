package eapli.base.productmanagement.repositories;

import eapli.base.productmanagement.domain.AlphaNumericCode;
import eapli.base.productmanagement.domain.ProductCategory;
import eapli.base.productmanagement.domain.ProductCategoryCode;
import eapli.framework.domain.repositories.DomainRepository;

public interface ProductCategoryRepository extends DomainRepository<ProductCategoryCode, ProductCategory> {

}
