package eapli.base.app.backoffice.console.presentation.order;

import eapli.base.infrastructure.persistence.PersistenceContext;
import eapli.base.ordersmanagement.application.RegisterOrderControllerImpl;
import eapli.framework.actions.Action;
import eapli.framework.infrastructure.authz.application.AuthzRegistry;

public class RegisterOrderAction implements Action {
    private final RegisterOrderUI registerOrderUI;

    public RegisterOrderAction(){
        var orderRepository = PersistenceContext.repositories().orders();

        var customerRepository = PersistenceContext.repositories().customers();

        var productRepository = PersistenceContext.repositories().products();

        var controller = new RegisterOrderControllerImpl(orderRepository,customerRepository,productRepository,AuthzRegistry.authorizationService());

        this.registerOrderUI = new RegisterOrderUI(controller);
    }

    @Override
    public boolean execute() {
        return this.registerOrderUI.show();
    }


}
