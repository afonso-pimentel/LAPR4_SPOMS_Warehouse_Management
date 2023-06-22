package eapli.base.app.backoffice.console.presentation.order;

import eapli.base.infrastructure.persistence.PersistenceContext;
import eapli.base.ordersmanagement.application.DispatchOrderControllerImpl;
import eapli.base.ordersmanagement.application.ChangeOrderStatusService;
import eapli.base.ordersmanagement.application.ListOrderService;
import eapli.framework.actions.Action;
import eapli.framework.infrastructure.authz.application.AuthzRegistry;

public class DispatchPreparedOrderAction implements Action {

    private final DispatchPreparedOrderUI dispatchPreparedOrderUI;

    public DispatchPreparedOrderAction(){
        var orderRepository = PersistenceContext.repositories().orders();

        var listOrderService = new ListOrderService(AuthzRegistry.authorizationService(), orderRepository);

        var changeOrderStatusService = new ChangeOrderStatusService(AuthzRegistry.authorizationService(), orderRepository);

        var controller = new DispatchOrderControllerImpl(orderRepository, listOrderService,changeOrderStatusService,AuthzRegistry.authorizationService());

        this.dispatchPreparedOrderUI = new DispatchPreparedOrderUI(controller);

    }

    @Override
    public boolean execute() {
        return  this.dispatchPreparedOrderUI.show();
    }
}
