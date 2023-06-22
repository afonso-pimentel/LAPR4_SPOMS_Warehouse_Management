package eapli.base.customerusermanagement.domain;

import eapli.framework.domain.model.ValueObject;
import eapli.framework.strings.util.StringPredicates;

import javax.persistence.Embeddable;
import java.util.Objects;

@Embeddable
public class Address implements ValueObject, Comparable<Address> {
    private String streetName;
    private String houseNumber;

    public void setStreetName(String streetName) {
        this.streetName = streetName;
    }

    public void setHouseNumber(String houseNumber) {
        this.houseNumber = houseNumber;
    }

    public void setCity(String city) {
        this.city = city;
    }

    private String city;

    public String getStreetName() {
        return streetName;
    }

    public String getHouseNumber() {
        return houseNumber;
    }

    public String getCity() {
        return city;
    }

    protected Address(final String streetName,final String houseNumber, final String city){
        if((city.length()>1) && (StringPredicates.isNullOrEmpty(streetName) || StringPredicates.isNullOrEmpty(houseNumber))){
            throw new IllegalArgumentException(
                    "City can't be filled and not have a street and door number"
            );
        }
        this.streetName = streetName;
        this.city = city;
        this.houseNumber = houseNumber;
    }

    protected Address(){
        //for ORM
    }

    public static Address valueOf(final String streetName,final String houseNumber, final String city){
        return new Address(streetName,houseNumber,city);
    }

    public Address clone(){
        return new Address(this.streetName, this.houseNumber, this.city);
    }

    @Override
    public String toString(){
        return "Street : "+ streetName +"\nHouse Number : "+ houseNumber +"\nCity : "+ city;
    }
    @Override
    public int hashCode(){
        return houseNumber.hashCode();
    }

    @Override
    public int compareTo(Address o){
        if(this.city.equals(o.city) && Objects.equals(this.houseNumber, o.houseNumber) && this.streetName.equals(o.streetName))
            return 1;
        else
            return 0;
    }
}
