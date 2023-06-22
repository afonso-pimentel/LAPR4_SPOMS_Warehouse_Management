package eapli.base.customerusermanagement.domain;

import eapli.framework.domain.model.ValueObject;
import eapli.framework.strings.util.StringPredicates;

import javax.persistence.Embeddable;
import java.util.Date;

@Embeddable
public class BirthDate implements ValueObject, Comparable<BirthDate> {


    private Date birthDate;

    public BirthDate(final Date birthDate){
        this.birthDate = birthDate;
    }

    protected BirthDate(){
        //for ORM
    }

    public static BirthDate valueOf(final Date birthDate){
        return new BirthDate(birthDate);
    }

    @Override
    public String toString(){
        return this.birthDate.toString();
    }
    @Override
    public int hashCode(){
        return this.birthDate.hashCode();
    }

    @Override
    public int compareTo(BirthDate o){
        return birthDate.compareTo(Date.from(o.birthDate.toInstant()));
    }
}
