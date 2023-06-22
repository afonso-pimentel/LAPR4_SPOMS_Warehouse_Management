package eapli.base.app.user.console.presentation.product;

import eapli.base.productmanagement.domain.Product;
import eapli.framework.visitor.Visitor;
import models.ProductDTO;

class ProductPrinter implements Visitor<ProductDTO> {

    @Override
    public void visit(final ProductDTO visitee) {
        System.out.printf("%-10s%-10s%-10s%-15s%-30s",
                visitee.internalCode.toString(),
                visitee.category.toString(),
                visitee.price,
                visitee.brand.toString(),
                visitee.shortDescription.toString());
    }
}
