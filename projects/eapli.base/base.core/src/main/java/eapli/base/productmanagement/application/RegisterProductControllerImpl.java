package eapli.base.productmanagement.application;

import com.sun.istack.NotNull;
import eapli.base.productmanagement.domain.*;

import eapli.base.productmanagement.repositories.ProductRepository;
import eapli.base.usermanagement.domain.BaseRoles;
import eapli.framework.application.UseCaseController;
import eapli.framework.domain.repositories.IntegrityViolationException;
import eapli.framework.general.domain.model.Designation;
import eapli.framework.general.domain.model.Money;
import eapli.framework.infrastructure.authz.application.AuthorizationService;
import eapli.framework.strings.util.StringPredicates;

import java.util.Set;

/**
 * The controller for the use case "Register New Product" using the domain objects.
 */
@UseCaseController
public class RegisterProductControllerImpl implements RegisterProductController {

    private final ProductRepository productRepository;
    private final ListProductCategoryService svcProductCategories;
    private final AuthorizationService authz;

    /**
     * Constructor for the controller.
     * @param repository
     * @param listProductCategoryService
     * @param authzService
     */
    public RegisterProductControllerImpl(@NotNull ProductRepository repository, @NotNull ListProductCategoryService listProductCategoryService, @NotNull AuthorizationService authzService){
        if(repository == null)
            throw new IllegalArgumentException("ProductRepository cannot be null");
        if(listProductCategoryService == null)
            throw new IllegalArgumentException("ListProductCategoryService cannot be null");
        if(authzService == null)
            throw new IllegalArgumentException("AuthorizationService cannot be null");
        this.productRepository = repository;
        this.svcProductCategories = listProductCategoryService;
        this.authz = authzService;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Product registerProduct(final String internalCode, final String brand, final String reference, final ProductCategory category,
                                   final String barcode, final byte[] bars, final double price, final String shortDescription, final String extendedDescription){
        return registerProduct(internalCode, brand, reference, category, barcode, bars, price, shortDescription, extendedDescription,
                    null, null, null,null,null, null, 0L);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Product registerProduct(final String internalCode, final String brand, final String reference, final ProductCategory category,
                                final String barcode, final byte[] bars, final double price, final String shortDescription,
                                final String extendedDescription, final String technicalDescription,
                                final String productCode, final Long aisle, final Long row, final Long shelf, Set<byte[]> photos, Long quantity){

        this.authz.ensureAuthenticatedUserHasAnyOf(BaseRoles.POWER_USER, BaseRoles.ADMIN, BaseRoles.SALES_CLERK);
        var code = AlphaNumericCode.valueOf(internalCode);
        if(this.productRepository.containsOfIdentity(code))
            throw new IntegrityViolationException("Product with the same id already exists in the repo.");

        // Mandatory elements
        final var newProduct = new ProductBuilder()
                .identifiedBy(code)
                .ofBrand(Designation.valueOf(brand))
                .ofReference(Designation.valueOf(reference))
                .fromProductCategory(category)
                .barcode(Barcode.valueOf(barcode, bars))
                .costing(Money.euros(price))
                .shortDescribedWith(ShortDescription.valueOf(shortDescription))
                .extendedDescribedWith(LongDescription.valueOf(extendedDescription));

        // Optional elements
        if(!StringPredicates.isNullOrEmpty(technicalDescription))
            newProduct.withTechnicalDescription(TechnicalDescription.valueOf(technicalDescription));
        if(aisle != null && row != null && shelf != null)
            newProduct.locatedIn(StorageArea.valueOf(aisle, row, shelf));
        if(photos!=null)
            newProduct.withPhotos(castByteToPhotos(photos));
        if(quantity >=0)
            newProduct.quantified(quantity);

        Product product = newProduct.build();

        return this.productRepository.save(product);

    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Iterable<ProductCategory> allProductCategories() { return svcProductCategories.allProductCategories(); }

    private Set<Photo> castByteToPhotos(final Set<byte[]> photos){
        Set<Photo> result = null;
        for (byte[] photo:photos) {
            Photo casted = Photo.valueOf(photo);
            result.add(casted);
        }
        return result;
    }

}
