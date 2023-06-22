package eapli.base.customerusermanagement.domain;

import eapli.framework.domain.model.DomainFactory;
import eapli.framework.general.domain.model.Designation;
import eapli.framework.general.domain.model.EmailAddress;

public class CustomerBuilder implements DomainFactory<Customer> {
    private Customer customer;

    //Mandatory
    private VatCode vatID;
    private Designation name;
    private EmailAddress email;
    private PhoneNumber phoneNumber;

    //Optional
    private Gender gender;
    private BirthDate birthDate;
    private Address billingAddress;
    private Address deliveryAddress;

    //Mandatory Attributes Building Methods
    public CustomerBuilder identifiedBy(VatCode vatID){
        this.vatID = vatID;
        return this;
    }
    public CustomerBuilder name(final Designation name){
        this.name = name;
        return this;
    }
    public CustomerBuilder email(final EmailAddress email){
        this.email = email;
        return this;
    }
    public CustomerBuilder phoneNumber(final PhoneNumber phoneNumber){
        this.phoneNumber = phoneNumber;
        return this;
    }

    private Customer buildOrThrow() {
        if (customer != null) {
            return customer;
        } else if (vatID != null && name != null && email != null && phoneNumber != null) {
            customer = new Customer(vatID, name, email, phoneNumber, gender != null && gender.toString().equals("") ? null : gender, birthDate, billingAddress != null && billingAddress.getCity().equals("") ? null: billingAddress, deliveryAddress != null && deliveryAddress.getCity().equals("") ? null:deliveryAddress);
            return customer;
        }else{
            throw new IllegalStateException();
        }
    }

    //Optional Attributes Building Methods

    public CustomerBuilder withGender(final Gender gender){
        this.gender = gender;
        return this;
    }

    public CustomerBuilder withBirthDate(final BirthDate birthDate){
        this.birthDate = birthDate;
        return this;
    }

    public CustomerBuilder withBillingAddress(final Address billingAddress){
        this.billingAddress = billingAddress;
        return this;
    }

    public CustomerBuilder withDeliveryAddress(final Address deliveryAddress){
        this.deliveryAddress = deliveryAddress;
        return this;
    }

    @Override
    public Customer build(){
        final Customer fit = buildOrThrow();
        customer = null;
        return fit;
    }
}
