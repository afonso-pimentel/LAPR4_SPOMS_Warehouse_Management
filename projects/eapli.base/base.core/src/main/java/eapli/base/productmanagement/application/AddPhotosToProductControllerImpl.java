package eapli.base.productmanagement.application;

import com.sun.istack.NotNull;
import eapli.base.productmanagement.domain.AlphaNumericCode;
import eapli.base.productmanagement.domain.Photo;
import eapli.base.productmanagement.domain.Product;
import eapli.base.productmanagement.repositories.ProductRepository;
import eapli.base.usermanagement.domain.BaseRoles;
import eapli.framework.application.UseCaseController;
import eapli.framework.infrastructure.authz.application.AuthorizationService;

import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.Set;

@UseCaseController
public class AddPhotosToProductControllerImpl implements AddPhotosToProductController {

    private final ProductRepository productRepository;
    private final AuthorizationService authz;

    public AddPhotosToProductControllerImpl(@NotNull ProductRepository productRepository,@NotNull AuthorizationService authzService) {
        if(authzService == null)
            throw new IllegalArgumentException("AuthorizationService cannot be null");
        if(productRepository == null)
            throw new IllegalArgumentException("ProductRepository cannot be null");
        this.authz = authzService;
        this.productRepository = productRepository;
    }


    @Override
    public Product addPhotosToProduct(String productId, Set<byte[]> photos){
        this.authz.ensureAuthenticatedUserHasAnyOf(BaseRoles.POWER_USER, BaseRoles.ADMIN, BaseRoles.SALES_CLERK);
        Optional<Product> productOptional = productRepository.ofIdentity(AlphaNumericCode.valueOf(productId));
        try{
            Product product = productOptional.get();
            for (byte[] photo:photos) {
                product.addPhotoOfProduct(Photo.valueOf(photo));
            }
            return productRepository.save(product);
        } catch (NoSuchElementException e) {
            //Exception treatment
        }
        return null;
    }
}
