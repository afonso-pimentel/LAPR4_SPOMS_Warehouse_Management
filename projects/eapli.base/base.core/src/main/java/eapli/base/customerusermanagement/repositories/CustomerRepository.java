package eapli.base.customerusermanagement.repositories;

import eapli.base.customerusermanagement.domain.Customer;
import eapli.base.customerusermanagement.domain.Gender;
import eapli.base.customerusermanagement.domain.PhoneNumber;
import eapli.base.customerusermanagement.domain.VatCode;
import eapli.framework.domain.repositories.DomainRepository;
import eapli.framework.general.domain.model.EmailAddress;

import java.util.Optional;

public interface CustomerRepository extends DomainRepository<VatCode, Customer>{

    Optional<Customer> findEquals(final VatCode vatCode, final PhoneNumber phoneNumber, final EmailAddress emailAddress);

    /**
     * Find's customer based on a vat code
     * @param vatCode Vat Code
     * @return Customer
     */
    Optional<Customer> findByVatCode(final VatCode vatCode);

    /**
     * Find's list of customers that have an age within the specified interval
     * @param startInterval Age start interval
     * @param endInterval Age end interval
     * @return List of Customers
     */
    Iterable<Customer> findByAgeInterval(final int startInterval, final int endInterval);

    /**
     * Find's list of customers that have a specific gender
     * @param gender Gender
     * @return List of Customers
     */
    Iterable<Customer> findByGender(final Gender gender);
}
