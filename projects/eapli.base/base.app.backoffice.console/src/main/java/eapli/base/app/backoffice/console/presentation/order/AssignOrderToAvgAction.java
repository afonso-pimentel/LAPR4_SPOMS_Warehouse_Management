package eapli.base.app.backoffice.console.presentation.order;

import eapli.base.infrastructure.persistence.PersistenceContext;
import eapli.base.ordersmanagement.application.AssignOrderToAgvControllerImpl;
import eapli.base.ordersmanagement.application.ChangeOrderStatusService;
import eapli.base.ordersmanagement.application.ListOrderService;
import eapli.base.warehousemanagement.application.AgvService;
import eapli.framework.actions.Action;
import eapli.framework.infrastructure.authz.application.AuthzRegistry;

public class AssignOrderToAvgAction implements Action {
    AssignOrderToAgvUI assignOrderToAgvUI;
    public AssignOrderToAvgAction(){
        var orderRepository = PersistenceContext.repositories().orders();

        var autz = AuthzRegistry.authorizationService();

        var listOrderService = new ListOrderService(autz, orderRepository);

        var agvService = new AgvService(autz, PersistenceContext.repositories().agvs());

        var changeOrderStatusService = new ChangeOrderStatusService(AuthzRegistry.authorizationService(), orderRepository);

        var controller = new AssignOrderToAgvControllerImpl(listOrderService,changeOrderStatusService,AuthzRegistry.authorizationService(),agvService);

        this.assignOrderToAgvUI = new AssignOrderToAgvUI(controller);
    }

    @Override
    public boolean execute() {
        return assignOrderToAgvUI.doShow();
    }
}
