package eapli.base.app.backoffice.console.presentation.order;

import eapli.base.infrastructure.persistence.PersistenceContext;
import eapli.base.ordersmanagement.application.ChangeOrderStatusService;
import eapli.base.ordersmanagement.application.ListOrderService;
import eapli.base.ordersmanagement.application.MarkAsDeliveredControllerImpl;
import eapli.framework.actions.Action;
import eapli.framework.infrastructure.authz.application.AuthzRegistry;

public class MarkAsDeliveredAction implements Action {

    private final MarkAsDeliveredUI markAsDeliveredUI;

    public MarkAsDeliveredAction(){
        var orderRepository = PersistenceContext.repositories().orders();

        var listOrderService = new ListOrderService(AuthzRegistry.authorizationService(), orderRepository);

        var changeOrderStatusService = new ChangeOrderStatusService(AuthzRegistry.authorizationService(), orderRepository);

        var controller = new MarkAsDeliveredControllerImpl(orderRepository, listOrderService,changeOrderStatusService,AuthzRegistry.authorizationService());

        this.markAsDeliveredUI = new MarkAsDeliveredUI(controller);

    }

    @Override
    public boolean execute() {
        return  this.markAsDeliveredUI.show();
    }
}
