package eapli.base.app.backoffice.console.presentation.product;

import eapli.base.productmanagement.domain.Product;
import eapli.framework.visitor.Visitor;

class ProductPrinter implements Visitor<Product> {

    @Override
    public void visit(final Product visitee) {
        System.out.printf("%-10s%-10s%-10s%-15s%-30s",
                visitee.identity().toString(),
                visitee.category().identity().toString(),
                visitee.price().toString(),
                visitee.brand().toString(),
                visitee.shortDescription().toString());
    }
}
