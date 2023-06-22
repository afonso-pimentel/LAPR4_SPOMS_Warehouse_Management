package eapli.base.customerusermanagement.domain;

import eapli.framework.domain.model.ValueObject;
import eapli.framework.strings.util.StringPredicates;

import javax.persistence.Embeddable;

@Embeddable
public class Gender implements ValueObject, Comparable<Gender> {


    private String gender;

    protected Gender(final String gender){
        this.gender = gender;
    }

    protected Gender(){
        //for ORM
    }

    public static Gender valueOf(final String gender){
        return new Gender(gender);
    }

    @Override
    public String toString(){
        return gender;
    }
    @Override
    public int hashCode(){
        return gender.hashCode();
    }

    @Override
    public int compareTo(Gender o){
        return gender.equals(o) ? 1:0;
    }
}
