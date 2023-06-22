package eapli.base.customerusermanagement.domain;

import eapli.framework.domain.model.ValueObject;
import eapli.framework.strings.util.StringPredicates;

import javax.persistence.Embeddable;

@Embeddable
public class PhoneNumber implements ValueObject, Comparable<PhoneNumber> {


    private long phoneNumber;

    protected PhoneNumber(final long phoneNumber){
        if(StringPredicates.isNullOrEmpty(String.valueOf(phoneNumber))){
            throw new IllegalArgumentException(
                    "A phoneNumber should neither be null nor empty"
            );
        }
        if(phoneNumber > 999999999 || phoneNumber < 100000000){
            throw new IllegalArgumentException(
                    "A phoneNumber shouldn't have more or less than 9 numbers"
            );
        }
        this.phoneNumber = phoneNumber;
    }

    protected PhoneNumber(){
        //for ORM
    }

    public static PhoneNumber valueOf(final long phoneNumber){
        return new PhoneNumber(phoneNumber);
    }

    @Override
    public String toString(){
        return String.valueOf(phoneNumber);
    }
    @Override
    public int hashCode(){
        return (int) this.phoneNumber;
    }

    @Override
    public int compareTo(PhoneNumber o){
        if(phoneNumber == o.phoneNumber)
            return 0;
        else
            return phoneNumber > o.phoneNumber ? 1 : -1;
    }
}
