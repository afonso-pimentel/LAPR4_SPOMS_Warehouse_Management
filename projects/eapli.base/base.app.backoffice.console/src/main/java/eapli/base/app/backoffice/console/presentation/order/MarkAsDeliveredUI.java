package eapli.base.app.backoffice.console.presentation.order;

import com.sun.istack.NotNull;
import eapli.base.ordersmanagement.application.MarkAsDeliveredController;
import eapli.base.ordersmanagement.domain.Order_;
import eapli.framework.presentation.console.AbstractUI;
import eapli.framework.presentation.console.SelectWidget;

public class MarkAsDeliveredUI extends AbstractUI {
    private final MarkAsDeliveredController controller;

    public MarkAsDeliveredUI(@NotNull MarkAsDeliveredController makeAsDeliveredController) {
        this.controller = makeAsDeliveredController;
    }

    @Override
    protected boolean doShow() {
        final Iterable<Order_> dispatchedOrders = this.controller.allDispatchedOrders();
        if(!dispatchedOrders.iterator().hasNext()) {
            System.out.println("There are no orders dispatched...");
            return false;
        }

        final SelectWidget<Order_> orderSelector = new SelectWidget<>("Choose one dispatched Orders:", dispatchedOrders, new OrderPrinter());
        orderSelector.show();
        if (orderSelector.selectedOption() != 0)
            this.controller.deliveredOrder(orderSelector.selectedElement());

        System.out.println("");
        System.out.println("Order changed to Delivered...");
        System.out.println("");

        return false;
    }

    @Override
    public String headline() {
        return "Mark Order as Delivered";
    }
}
