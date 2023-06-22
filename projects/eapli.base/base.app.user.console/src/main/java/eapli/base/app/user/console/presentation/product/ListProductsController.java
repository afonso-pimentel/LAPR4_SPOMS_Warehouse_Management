package eapli.base.app.user.console.presentation.product;

import eapli.base.productmanagement.domain.Product;
import models.ProductDTO;

public interface ListProductsController {
    Iterable<ProductDTO> sortAndFilterProducts(String filterField, String filter, String sortField, Boolean ascending);
}
