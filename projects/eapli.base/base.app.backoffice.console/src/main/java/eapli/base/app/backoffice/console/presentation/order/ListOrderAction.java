package eapli.base.app.backoffice.console.presentation.order;

import eapli.base.infrastructure.persistence.PersistenceContext;
import eapli.base.ordersmanagement.application.ListOrderControllerImpl;
import eapli.framework.actions.Action;
import eapli.framework.infrastructure.authz.application.AuthzRegistry;

public class ListOrderAction implements Action {

    private final ListOrderUI listOrderUI;

    public ListOrderAction(){
        var orderRepository = PersistenceContext.repositories().orders();

        var customerRepository = PersistenceContext.repositories().customers();

        var controller = new ListOrderControllerImpl(orderRepository, customerRepository,AuthzRegistry.authorizationService());

        this.listOrderUI = new ListOrderUI(controller);

    }

    @Override
    public boolean execute() {
        return this.listOrderUI.show();
    }
}
