package eapli.base.app.backoffice.console.presentation.order;

import eapli.base.ordersmanagement.domain.OrderLine;
import eapli.framework.visitor.Visitor;

public class OrderLinePrinter implements Visitor<OrderLine> {
    @Override
    public void visit(OrderLine visitee) {
        System.out.println(visitee.toString());
    }
}
