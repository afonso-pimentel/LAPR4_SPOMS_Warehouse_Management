package eapli.base.infrastructure.bootstrapers.demo;

import eapli.base.customerusermanagement.application.RegisterCustomerController;
import eapli.base.customerusermanagement.application.RegisterCustomerControllerImpl;
import eapli.base.infrastructure.persistence.PersistenceContext;
import eapli.framework.domain.repositories.ConcurrencyException;
import eapli.framework.domain.repositories.IntegrityViolationException;
import eapli.framework.infrastructure.authz.application.AuthzRegistry;
import eapli.framework.actions.Action;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.Instant;
import java.util.Date;

public class CustomerBootstrapper implements Action{
    private static final Logger LOGGER = LoggerFactory.getLogger(
            CustomerBootstrapper.class);

    private final RegisterCustomerController registerCustomerController;

    public CustomerBootstrapper(){
        var customerRepository = PersistenceContext.repositories().customers();

        this.registerCustomerController = new RegisterCustomerControllerImpl(customerRepository, AuthzRegistry.authorizationService());

    }

    @Override
    public boolean execute() {
        registerCustomer("123456789","John","johnny@gmail.com","913456789","Male",
                Date.from(Instant.now()),"Paris", "Rue ScerCours", "312",
                "Paris", "Rue ScerCours", "312" );
        registerCustomer("123333789","Marie","marieanglaish@gmail.com","912333379","non-binary",
                Date.from(Instant.now()),"Bruxelas", "Street Middle Town", "312",
                "Bruxelas", "Street Middle Town", "312");
        registerCustomer("987654321","Anthony","misterglass@gmail.com","912381954","Female",
                Date.from(Instant.now()),"Munich", "Street of liberte", "456",
                "Munich", "Street of liberte", "456");
        registerCustomer("987654321","Julie","statstichs@gmail.com","942123953","Trans",
                Date.from(Instant.now()),"Munich", "Street of liberte", "456",
                "Munich", "Street of liberte", "456");

        return true;
    }

    private void registerCustomer(final String vatID, final String name, final String email,
                                  final String phoneNumber, final String gender, final Date birthDate,
                                  final String cityBillingAddress, final String streetNBillingAddress,
                                  final String doorNBillingAddress, final String cityDeliveryAddress,
                                  final String streetNDeliveryAddress,final String doorNDeliveryAddress){
        try {
            this.registerCustomerController.registerCustomer(vatID,name,email,
            phoneNumber,gender,birthDate,
            cityBillingAddress,streetNBillingAddress,
            doorNBillingAddress,cityDeliveryAddress,
            streetNDeliveryAddress,doorNDeliveryAddress);

        } catch (final ConcurrencyException | IntegrityViolationException e) {
            // ignoring exception. assuming it is just a primary key violation
            // due to the tentative of inserting a duplicated user
            LOGGER.warn("Assuming {} already exists (activate trace log for details)", vatID);
            LOGGER.trace("Assuming existing record", e);
        }
    }
}
