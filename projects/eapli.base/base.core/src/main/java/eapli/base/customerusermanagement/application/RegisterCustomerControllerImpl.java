package eapli.base.customerusermanagement.application;

import com.sun.istack.NotNull;
import eapli.base.customerusermanagement.domain.*;
import eapli.base.customerusermanagement.repositories.CustomerRepository;
import eapli.base.infrastructure.persistence.PersistenceContext;
import eapli.base.productmanagement.repositories.ProductCategoryRepository;
import eapli.base.usermanagement.domain.BaseRoles;
import eapli.framework.application.UseCaseController;

import eapli.framework.domain.repositories.IntegrityViolationException;
import eapli.framework.general.domain.model.Designation;
import eapli.framework.general.domain.model.EmailAddress;

import eapli.framework.infrastructure.authz.application.AuthorizationService;
import eapli.framework.infrastructure.authz.application.AuthzRegistry;
import eapli.framework.strings.util.StringPredicates;

import java.util.Date;

/**
 * The controller for the use case "Register New Customer" using the domain objects.
 */
@UseCaseController
public class RegisterCustomerControllerImpl implements RegisterCustomerController{
    private final AuthorizationService authz;
    private final CustomerRepository customerRepository;

    public RegisterCustomerControllerImpl(@NotNull CustomerRepository repository, @NotNull AuthorizationService authzService){
        if(repository == null)
            throw new IllegalArgumentException("CustomerRepository cannot be null");

        if(authzService == null)
            throw new IllegalArgumentException("AuthorizationService cannot be null");

        this.customerRepository = repository;
        this.authz = authzService;
    }

    public Customer registerCustomer(final String vatID, final String name, final String email,
                                     final String phoneNumber){
        String onlyObligatory = "";
        return registerCustomer(vatID,name,email,phoneNumber,
                onlyObligatory,null,onlyObligatory,onlyObligatory,onlyObligatory,onlyObligatory,onlyObligatory,onlyObligatory);
    }

    public Customer registerCustomer(final String vatID, final String name, final String email,
                                     final String phoneNumber, final String gender, final Date birthDate,
                                     final String cityBillingAddress, final String streetNBillingAddress,
                                     final String doorNBillingAddress, final String cityDeliveryAddress,
                                     final String streetNDeliveryAddress,final String doorNDeliveryAddress){


        this.authz.ensureAuthenticatedUserHasAnyOf(BaseRoles.POWER_USER, BaseRoles.ADMIN, BaseRoles.SALES_CLERK);

        final var newCustomerBuilder = new CustomerBuilder()
                .identifiedBy(VatCode.valueOf(vatID))
                .name(Designation.valueOf(name))
                .email(EmailAddress.valueOf(email))
                .phoneNumber(PhoneNumber.valueOf(Long.parseLong(phoneNumber)));

        if(!StringPredicates.isNullOrEmpty(streetNBillingAddress) &&
           !StringPredicates.isNullOrEmpty(doorNBillingAddress) &&
           !StringPredicates.isNullOrEmpty(cityBillingAddress)){
            newCustomerBuilder.withBillingAddress(Address.valueOf(streetNBillingAddress,doorNBillingAddress,cityBillingAddress));
        }

        if(!StringPredicates.isNullOrEmpty(streetNDeliveryAddress) &&
                !StringPredicates.isNullOrEmpty(doorNDeliveryAddress) &&
                !StringPredicates.isNullOrEmpty(cityDeliveryAddress)){
            newCustomerBuilder.withDeliveryAddress(Address.valueOf(streetNDeliveryAddress,doorNDeliveryAddress,cityDeliveryAddress));
        }

        if(birthDate != null){
            newCustomerBuilder.withBirthDate(BirthDate.valueOf(birthDate));
        }


        if(!StringPredicates.isNullOrEmpty(gender)){
            newCustomerBuilder.withGender(Gender.valueOf(gender));
        }

        final var newCustomer = newCustomerBuilder.build();

        if (customerRepository.findEquals(VatCode.valueOf(vatID),PhoneNumber.valueOf(Long.parseLong(phoneNumber)),EmailAddress.valueOf(email)).isPresent()){
            throw new IntegrityViolationException(" This customer is already registered in the system, try using a different vatID, phoneNumber or email.");
        }

        return customerRepository.save(newCustomer);
    }

}

