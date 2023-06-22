package eapli.base.customerusermanagement.application;

import eapli.base.customerusermanagement.domain.Customer;

import java.util.Date;

public interface RegisterCustomerController {
    Customer registerCustomer(final String vatID, final String name, final String email,
                              final String phoneNumber);
    Customer registerCustomer(final String vatID, final String name, final String email,
                              final String phoneNumber, final String gender, final Date birthDate,
                              final String cityBillingAddress, final String streetNBillingAddress,
                              final String doorNBillingAddress, final String cityDeliveryAddress,
                              final String streetNDeliveryAddress, final String doorNDeliveryAddress);
}
