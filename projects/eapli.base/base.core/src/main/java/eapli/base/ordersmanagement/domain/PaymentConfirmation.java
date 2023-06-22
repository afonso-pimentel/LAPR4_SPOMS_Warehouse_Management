package eapli.base.ordersmanagement.domain;

import com.fasterxml.jackson.annotation.JsonProperty;
import eapli.framework.domain.model.ValueObject;

import javax.persistence.Lob;
import javax.xml.bind.annotation.XmlAttribute;
import java.util.Arrays;

public class PaymentConfirmation implements ValueObject, Comparable<PaymentConfirmation> {

    @Lob
    private final byte[] confirmation;

    protected PaymentConfirmation(final byte[] confirmation){
        this.confirmation = confirmation;
    }

    protected PaymentConfirmation(){
        // For ORM
        confirmation = null;
    }

    public static PaymentConfirmation valueOf(final byte[] confirmation) { return new PaymentConfirmation(confirmation); }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PaymentConfirmation that = (PaymentConfirmation) o;
        return Arrays.equals(confirmation, that.confirmation);
    }

    @Override
    public int hashCode() {
        return Arrays.hashCode(this.confirmation);
    }

    @Override
    public String toString(){
        return this.confirmation.toString();
    }

    /**
     * it either returns 0 or 1. in this case it is irrelevant for it to return a different value.
     * @param o
     * @return
     */
    @Override
    public int compareTo(PaymentConfirmation o) {
        if(this.confirmation.equals(o.confirmation)) { return 0;}
        return 1;
    }
}
