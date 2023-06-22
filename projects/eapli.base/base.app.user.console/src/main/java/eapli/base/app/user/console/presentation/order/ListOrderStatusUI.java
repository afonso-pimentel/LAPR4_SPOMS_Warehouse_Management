package eapli.base.app.user.console.presentation.order;

import eapli.base.customerusermanagement.domain.VatCode;
import eapli.framework.presentation.console.AbstractUI;
import models.OrderDTO;

import java.util.ArrayList;
import java.util.Collection;

import static eapli.base.app.user.console.BaseUserApp.vatCode;

public class ListOrderStatusUI extends AbstractUI {
    private ListOrderController theController = new ListOrderControllerImpl();
    @Override
    protected boolean doShow() {
        Iterable<OrderDTO> orderListI = theController.orderStatusFromClient(VatCode.valueOf(String.valueOf(vatCode)));
        ArrayList<OrderDTO> orderList = new ArrayList((Collection) orderListI);
        if(orderList.size() >= 1){
            for (OrderDTO pr: orderList) { System.out.println(pr.toString());}
        }else{
            System.out.println("This Client doesn't have any Orders");
        }

        return true;
    }

    @Override
    public String headline() {
        return "Order Status";
    }
}
