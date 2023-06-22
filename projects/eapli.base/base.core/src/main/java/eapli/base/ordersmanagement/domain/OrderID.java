package eapli.base.ordersmanagement.domain;

import eapli.framework.domain.model.ValueObject;
import eapli.framework.strings.util.StringPredicates;

import javax.persistence.Embeddable;
import java.util.Objects;

@Embeddable
public class OrderID implements ValueObject, Comparable<OrderID> {

    private String orderId;

    public OrderID(final String orderId){
        if(StringPredicates.isNullOrEmpty(orderId)){
            throw new IllegalArgumentException("An Order ID should neither be null nor empty.");
        }
        this.orderId = orderId;
    }

    protected OrderID(){
        // For ORM
    }

    public static OrderID valueOf(final String orderId) { return new OrderID(orderId); }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        OrderID orderID = (OrderID) o;
        return Objects.equals(orderId, orderID.orderId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(orderId);
    }

    @Override
    public int compareTo(OrderID o) {
        return orderId.compareTo(o.orderId);
    }
}
