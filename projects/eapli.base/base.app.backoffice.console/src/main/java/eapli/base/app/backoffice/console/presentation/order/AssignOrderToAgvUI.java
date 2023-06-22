package eapli.base.app.backoffice.console.presentation.order;

import com.sun.istack.NotNull;
import eapli.base.ordersmanagement.domain.Order_;
import eapli.base.ordersmanagement.application.AssignOrderToAgvController;
import eapli.base.warehousemanagement.domain.Agv;
import eapli.framework.presentation.console.AbstractUI;
import eapli.framework.presentation.console.SelectWidget;

public class AssignOrderToAgvUI extends AbstractUI {

    private final AssignOrderToAgvController controller;

    public AssignOrderToAgvUI(@NotNull AssignOrderToAgvController assignOrderToAgvController) {
        this.controller = assignOrderToAgvController;
    }


    @Override
    protected boolean doShow() {
        System.out.println("Choose one of the to be prepared orders:");
        final Iterable<Order_> preparedOrders = this.controller.allRegisteredOrders();
        final SelectWidget<Order_> orderSelector = new SelectWidget<>("To Be Prepared Orders:", preparedOrders, new OrderPrinter());
        orderSelector.show();
        if(orderSelector.selectedOption() == 0) {
            System.out.println("No order has been chosen... We can't continue.");
            return false;
        }
        final SelectWidget<Agv> agvSelector = new SelectWidget<>("Available AGVs:", this.controller.availableAgvs(), new AgvPrinter());
        agvSelector.show();
        if(agvSelector.selectedOption() == 0) {
            System.out.println("No agv has been chosen... We can't continue.");
            return false;
        }
        if(orderSelector.selectedOption()!=0) {
            this.controller.assignOrderToAgv(orderSelector.selectedElement(), agvSelector.selectedElement());
            System.out.println("The order is in preparation.");

        }
        return false;
    }

    @Override
    public String headline() {
        return "Assign Order to an AVG";
    }
}
