package eapli.base.productmanagement.application;

import eapli.base.productmanagement.domain.Product;

public interface ListProductController {
    Iterable<Product> sortAndFilterProducts(String filterField, String filter, String sortField, Boolean ascending);
}
