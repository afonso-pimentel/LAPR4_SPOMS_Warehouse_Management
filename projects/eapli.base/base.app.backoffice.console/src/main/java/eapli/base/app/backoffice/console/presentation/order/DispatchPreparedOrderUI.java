package eapli.base.app.backoffice.console.presentation.order;

import com.sun.istack.NotNull;
import eapli.base.ordersmanagement.application.DispatchOrderController;
import eapli.base.ordersmanagement.domain.Order_;
import eapli.framework.presentation.console.AbstractUI;
import eapli.framework.presentation.console.SelectWidget;

public class DispatchPreparedOrderUI extends AbstractUI {

    private final DispatchOrderController controller;

    public DispatchPreparedOrderUI(@NotNull DispatchOrderController changeOrderStatusController) {
        this.controller = changeOrderStatusController;
    }


    @Override
    protected boolean doShow() {
        System.out.println("Choose one of the prepared orders to change the status:");
        final Iterable<Order_> preparedOrders = this.controller.allPreparedOrders();
        final SelectWidget<Order_> orderSelector = new SelectWidget<>("Prepared Orders:", preparedOrders, new OrderPrinter());
        orderSelector.show();
        if(orderSelector.selectedOption()!=0) {
            final Order_ orderToDispatch = orderSelector.selectedElement();
            this.controller.dispatchOrder(orderToDispatch);
        }
        return false;
    }

    @Override
    public String headline() {
        return "Dispatch Prepared Order";
    }
}
