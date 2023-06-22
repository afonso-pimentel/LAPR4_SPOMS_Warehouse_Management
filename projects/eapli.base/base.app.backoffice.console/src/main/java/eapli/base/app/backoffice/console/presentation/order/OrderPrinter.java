package eapli.base.app.backoffice.console.presentation.order;

import eapli.base.ordersmanagement.domain.Order_;
import eapli.framework.visitor.Visitor;

public class OrderPrinter implements Visitor<Order_> {

    @Override
    public void visit(final Order_ visitee) {
        System.out.println(visitee.toString());
    }
}
