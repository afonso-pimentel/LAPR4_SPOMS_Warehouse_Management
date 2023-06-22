package eapli.base.productmanagement.application;

import eapli.base.productmanagement.domain.Product;

import java.util.Set;

public interface AddPhotosToProductController {

    /**
     *
     * @param productId
     * @param photos
     * @return
     */
    Product addPhotosToProduct(String productId, Set<byte[]> photos);
}
