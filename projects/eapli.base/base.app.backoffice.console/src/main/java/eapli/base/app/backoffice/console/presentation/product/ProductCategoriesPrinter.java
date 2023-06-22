package eapli.base.app.backoffice.console.presentation.product;

import eapli.base.productmanagement.domain.ProductCategory;
import eapli.framework.visitor.Visitor;

class ProductCategoriesPrinter implements Visitor<ProductCategory> {

    @Override
    public void visit(final ProductCategory visitee) {
        System.out.printf("%-10s %-50s", visitee.identity(), visitee.description());
    }
}
