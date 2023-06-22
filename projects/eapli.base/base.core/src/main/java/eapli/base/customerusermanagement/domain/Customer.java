package eapli.base.customerusermanagement.domain;

import eapli.framework.domain.model.AggregateRoot;
import eapli.framework.domain.model.DomainEntities;
import eapli.framework.general.domain.model.Designation;
import eapli.framework.general.domain.model.EmailAddress;
import eapli.framework.representations.RepresentationBuilder;
import eapli.framework.representations.Representationable;
import eapli.framework.validations.Preconditions;

import javax.persistence.*;

@Entity
public class Customer implements AggregateRoot<VatCode>, Representationable {
    @Id
    @AttributeOverride(name = "code", column = @Column(name = "vatID"))
    private VatCode vatID;

    @AttributeOverride(name = "name", column = @Column(name = "name"))
    private Designation name;

    @AttributeOverride(name = "email", column = @Column(name = "email"))
    private EmailAddress email;

    @AttributeOverride(name = "phoneNumber", column = @Column(name = "phoneNumber"))
    private PhoneNumber phoneNumber;

    @AttributeOverride(name = "gender", column = @Column(name = "gender"))
    private Gender gender;

    @AttributeOverride(name = "birthDate", column = @Column(name = "birthDate"))
    private BirthDate birthDate;

    @AttributeOverride(name = "streetName", column = @Column(name = "streetNameBillingAddress"))
    @AttributeOverride(name = "houseNumber", column = @Column(name = "houseNumberBillingAddress"))
    @AttributeOverride(name = "city", column = @Column(name = "cityBillingAddress"))
    private Address billingAddress;

    @AttributeOverride(name = "streetName", column = @Column(name = "streetNameDeliveryAddress"))
    @AttributeOverride(name = "houseNumber", column = @Column(name = "houseNumberDeliveryAddress"))
    @AttributeOverride(name = "city", column = @Column(name = "cityDeliveryAddress"))
    private Address deliveryAddress;

    public Customer(VatCode vatID, Designation name, EmailAddress email, PhoneNumber phoneNumber, Gender gender, BirthDate birthDate, Address billingAddress, Address deliveryAddress){
        Preconditions.noneNull(vatID,name,email,phoneNumber);

        this.vatID = vatID;
        this.name = name;
        this.email = email;
        this.phoneNumber = phoneNumber;

        //Optional Fields
        this.gender = gender;
        this.birthDate = birthDate;
        this.billingAddress = billingAddress;
        this.deliveryAddress =deliveryAddress;
    }

    protected Customer(){
        //For ORM
    }

    @Override
    public boolean equals(final Object o){
        return DomainEntities.areEqual(this,o); }

    @Override
    public boolean sameAs(final Object other){
        if(!(other instanceof Customer)){
            return  false;
        }
        final Customer that = (Customer) other;
        if(this == that)
            return true;

        return vatID.equals(that.vatID) && name.equals(that.name) && email.equals(that.email) && phoneNumber.equals(that.phoneNumber) && gender.equals(that.gender)
                && birthDate.equals(that.birthDate) && billingAddress.equals(that.billingAddress) && deliveryAddress.equals(that.deliveryAddress);
    }

    @Override
    public int compareTo(VatCode other){
        return AggregateRoot.super.compareTo(other);
    }

    @Override
    public VatCode identity(){
        return this.vatID;
    }

    @Override
    public boolean hasIdentity(VatCode id){
        return AggregateRoot.super.hasIdentity(id);
    }

    /** buildder of Customer
     * @param builder
     * @param <R>
     * @return
     */
    public <R> R buildRepresentation(final RepresentationBuilder<R> builder){
        builder.startObject("Customer")
                .withProperty("vatID", String.valueOf(vatID))
                .withProperty("name", name)
                .withProperty("email", email)
                .withProperty("phoneNumber", String.valueOf(phoneNumber));
        if(gender != null) builder.withProperty("gender", String.valueOf(gender));
        if(birthDate != null) builder.withProperty("birthDate", String.valueOf(birthDate));
        if(billingAddress != null) builder.withProperty("billingAddress", String.valueOf(billingAddress));
        if(deliveryAddress != null) builder.withProperty("deliveryAddress", String.valueOf(deliveryAddress));

        builder.endObject();
        return builder.build();
    }

    public void update(final Designation name, final EmailAddress email, final PhoneNumber phoneNumber,
                       final Gender gender,
                       final BirthDate birthDate, final Address billingAddress, final Address deliveryAddress)
    {
        Preconditions.noneNull(name,email,phoneNumber);
        this.name = name;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.gender = gender;
        this.birthDate = birthDate;
        this.billingAddress = billingAddress;
        this.deliveryAddress = deliveryAddress;
        changeNameto(name);
        changeEmailto(email);
        changePhoneNumberto(phoneNumber);
        changeGenderto(gender);
        changeBirthDateto(birthDate);
        changeBillingAddressto(billingAddress);
        changeDeliveryAddressto(deliveryAddress);
    }

    public Designation name() {
        return this.name;
    }

    public EmailAddress email() {
        return this.email;
    }

    public PhoneNumber phoneNumber() {
        return this.phoneNumber;
    }

    public Gender gender() {
        return this.gender;
    }

    public BirthDate birthDate() {
        return this.birthDate;
    }

    public Address billingAddress() {
        return this.billingAddress;
    }

    public Address deliveryAddress(){ return this.deliveryAddress; }

    public void changeNameto(final Designation newName){
        name = newName;
    }

    public void changeEmailto(final EmailAddress newEmail){
        email = newEmail;
    }

    public void changePhoneNumberto(final PhoneNumber newPhoneNumber){
        phoneNumber = newPhoneNumber;
    }

    public void changeGenderto(final Gender newGender){
        gender = newGender;
    }

    public void changeBirthDateto(final BirthDate newBirthDate){
        birthDate = newBirthDate;
    }

    public void changeBillingAddressto(final Address newBillingAddress){
        billingAddress = newBillingAddress;
    }

    public void changeDeliveryAddressto(final Address newDeliveryAddress){
        deliveryAddress = newDeliveryAddress;
    }

    @Override
    public int hashCode() {
        return vatID.hashCode();
    }
}
