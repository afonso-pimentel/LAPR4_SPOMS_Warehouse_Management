package eapli.base.app.backoffice.console.presentation.customer;

import eapli.base.customerusermanagement.application.RegisterCustomerControllerImpl;
import eapli.base.infrastructure.persistence.PersistenceContext;
import eapli.base.productmanagement.application.RegisterProductCategoryControllerImpl;
import eapli.framework.actions.Action;
import eapli.framework.infrastructure.authz.application.AuthzRegistry;

public class RegisterCustomerAction implements Action{
    @Override
    public  boolean execute(){
        var customersRepository = PersistenceContext.repositories().customers();

        var controller = new RegisterCustomerControllerImpl(customersRepository, AuthzRegistry.authorizationService());

        return new RegisterCustomerUI(controller).show();
    }
}
