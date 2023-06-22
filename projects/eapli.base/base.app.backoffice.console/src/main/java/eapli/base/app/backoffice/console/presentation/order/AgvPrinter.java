package eapli.base.app.backoffice.console.presentation.order;

import eapli.base.warehousemanagement.domain.Agv;
import eapli.framework.visitor.Visitor;

public class AgvPrinter implements Visitor<Agv> {

    @Override
    public void visit(final Agv visitee) {
        System.out.println(visitee.toString());
    }
}
