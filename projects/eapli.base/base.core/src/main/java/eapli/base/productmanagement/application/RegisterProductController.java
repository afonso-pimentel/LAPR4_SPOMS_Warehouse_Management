package eapli.base.productmanagement.application;

import eapli.base.productmanagement.domain.Product;
import eapli.base.productmanagement.domain.ProductCategory;

import java.util.Set;

public interface RegisterProductController {

    /**
     * RegisterProduct with all of the parameters needed to define the product.
     *
     * @param internalCode
     * @param brand
     * @param reference
     * @param category
     * @param barcode
     * @param bars
     * @param price
     * @param shortDescription
     * @param extendedDescription
     * @param technicalDescription
     * @param productCode
     * @param aisle
     * @param row
     * @param shelf
     * @param photos
     * @param quantity
     * @return
     */
    Product registerProduct(final String internalCode, final String brand, final String reference, final ProductCategory category,
                                   final String barcode, final byte[] bars, final double price, final String shortDescription,
                                   final String extendedDescription, final String technicalDescription,
                                   final String productCode, final Long aisle, final Long row, final Long shelf, Set<byte[]> photos, Long quantity);

    /**
     * RegisterProduct method with only the mandatory parameters.
     *
     * @param internalCode
     * @param brand
     * @param reference
     * @param category
     * @param barcode
     * @param bars
     * @param price
     * @param shortDescription
     * @param extendedDescription
     * @return
     */
    Product registerProduct(final String internalCode, final String brand, final String reference, final ProductCategory category,
                            final String barcode, final byte[] bars, final double price, final String shortDescription, final String extendedDescription);

    /**
     * Returns all product Categories
     * @return
     */
    Iterable<ProductCategory> allProductCategories();



}
