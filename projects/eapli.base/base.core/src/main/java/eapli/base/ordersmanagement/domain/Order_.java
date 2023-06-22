package eapli.base.ordersmanagement.domain;

import com.sun.istack.NotNull;
import eapli.base.customerusermanagement.domain.Address;
import eapli.base.customerusermanagement.domain.VatCode;
import eapli.framework.domain.model.AggregateRoot;
import eapli.framework.general.domain.model.Money;

import javax.persistence.*;
import java.util.Date;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

@Entity
public class Order_ implements AggregateRoot<Long> {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long orderId;

    private VatCode customerId;

    private ShipmentMethod shipmentMethod;

    private PaymentMethod paymentMethod;

    private PaymentConfirmation paymentConfirmation;

    private Address shipmentAddress;

    @Temporal(TemporalType.TIMESTAMP)
    private Date orderDate;

    @AttributeOverride(name = "amount", column = @Column(name = "totalBeforeTax"))
    @AttributeOverride(name = "currency", column = @Column(name = "total_before_tax_currency"))
    private Money totalBeforeTax;

    @AttributeOverride(name = "amount", column = @Column(name = "totalAfterTax"))
    @AttributeOverride(name = "currency", column = @Column(name = "total_after_tax_currency"))
    private Money totalAfterTax;

    @OneToMany(cascade = {CascadeType.ALL})
    private Set<OrderLine> orderLines;

    private OrderStatus orderStatus;

    public Order_(@NotNull Set<OrderLine> orderLines,@NotNull VatCode customerId,@NotNull ShipmentMethod shipmentMethod,@NotNull PaymentMethod paymentMethod,@NotNull
                  Address shipmentAddress,@NotNull Date orderDate) {
        if(orderLines == null){
            throw new IllegalArgumentException("Order Lines set cannot be null.");
        }
        if(customerId == null){
            throw new IllegalArgumentException("Customer ID cannot be null.");
        }
        if(shipmentMethod == null){
            throw new IllegalArgumentException("Shipment method cannot be null.");
        }
        if(paymentMethod == null){
            throw new IllegalArgumentException("Payment method cannot be null.");
        }
        if(shipmentAddress == null){
            throw new IllegalArgumentException("The customer's delivery address cannot be null.");
        }
        if(orderDate == null){
            throw new IllegalArgumentException("Date cannot be null.");
        }
        this.customerId = customerId;
        this.shipmentMethod = shipmentMethod;
        this.paymentMethod = paymentMethod;
        this.shipmentAddress = shipmentAddress.clone();
        this.orderDate = orderDate;
        this.orderLines = orderLines;
        this.totalBeforeTax = calculateTotalBeforeTax(orderLines);
        this.totalAfterTax = calculateTotalAfterTax(orderLines);
        this.orderStatus = OrderStatus.PAYMENT_PENDING;

        this.paymentConfirmation = null;
    }

    public Order_(@NotNull VatCode customerId,@NotNull ShipmentMethod shipmentMethod, @NotNull PaymentMethod paymentMethod,
                  @NotNull Address shipmentAddress, @NotNull Date orderDate) {
        if(customerId == null){
            throw new IllegalArgumentException("Customer ID cannot be null.");
        }
        if(shipmentMethod == null){
            throw new IllegalArgumentException("Shipment method cannot be null.");
        }
        if(paymentMethod == null){
            throw new IllegalArgumentException("Payment method cannot be null.");
        }
        if(shipmentAddress == null){
            throw new IllegalArgumentException("The customer's delivery address cannot be null.");
        }
        if(orderDate == null){
            throw new IllegalArgumentException("Date cannot be null.");
        }
        this.customerId = customerId;
        this.shipmentMethod = shipmentMethod;
        this.paymentMethod = paymentMethod;
        this.shipmentAddress = shipmentAddress.clone();
        this.orderDate = orderDate;
        this.orderLines = new HashSet<>();
        this.totalBeforeTax = calculateTotalBeforeTax(orderLines);
        this.totalAfterTax = calculateTotalAfterTax(orderLines);
        this.orderStatus = OrderStatus.PAYMENT_PENDING;

        this.paymentConfirmation = null;
    }

    protected Order_(){
        //For ORM
    }

    private Money calculateTotalBeforeTax(Set<OrderLine> orderLines){
        double totalBeforeTax = 0.00;
        for(OrderLine orderLine : orderLines){
            totalBeforeTax += orderLine.totalPriceBeforeTax().amountAsDouble();
        }
        return Money.euros(totalBeforeTax);
    }

    private Money calculateTotalAfterTax(Set<OrderLine> orderLines){
        double totalAfterTax = 0.00;
        for(OrderLine orderLine : orderLines){
                totalAfterTax += orderLine.totalPriceAfterTax().amountAsDouble();
        }
        return Money.euros(totalAfterTax);
    }

    public boolean confirmPayment(PaymentConfirmation paymentConfirmation){
        if(paymentConfirmation != null ){
            this.paymentConfirmation = paymentConfirmation;
            return true;
        }
        return false;
    }

    public Money totalBeforeTax() { return this.totalBeforeTax; }

    public OrderStatus orderStatus(){ return this.orderStatus; }

    public void changeOrderStatus(OrderStatus orderStatus){
        this.orderStatus = orderStatus;
    }

    public Set<OrderLine> orderLines(){
        return Collections.unmodifiableSet(this.orderLines);
    }

    public void setOrderLines(Set<OrderLine> orderLines){
        this.orderLines = orderLines;
        this.totalBeforeTax =  calculateTotalBeforeTax(orderLines);
        this.totalAfterTax = calculateTotalAfterTax(orderLines);
    }

    public void addOrderLine(OrderLine orderLine){
        this.orderLines.add(orderLine);
    }

    public VatCode customerId() { return this.customerId; }

    public ShipmentMethod shipmentMethod() { return this.shipmentMethod; }

    public PaymentMethod paymentMethod() { return this.paymentMethod; }

    public Date orderDate() { return this.orderDate; }

    @Override
    public String toString() {
        return  "#" + orderId.toString() +
                " || Customer #" + customerId.toString() +
                " || " + shipmentMethod.toString() +
                " || " + paymentMethod.toString() +
                " || " + shipmentAddress.toString() +
                " || Date: " + orderDate.toString() +
                " || Status: " + orderStatus.toString();
    }

    @Override
    public boolean sameAs(Object other) {
        if (!(other instanceof Order_)) {
            return false;
        }

        final Order_ that = (Order_) other;
        if (this == that) {
            return true;
        }

        return identity().equals(that.identity()) && customerId.equals(that.customerId)
                && shipmentMethod.equals(that.shipmentMethod) && paymentMethod.equals(that.paymentMethod)
                && orderDate.equals(that.orderDate) && orderLines.equals(that.orderLines)
                && totalBeforeTax.equals(totalBeforeTax) && totalAfterTax.equals(that.totalAfterTax);
    }

    @Override
    public int compareTo(Long other) {
        return AggregateRoot.super.compareTo(other);
    }

    @Override
    public Long identity() {
        return this.orderId;
    }

    @Override
    public boolean hasIdentity(Long id) {
        return AggregateRoot.super.hasIdentity(id);
    }
}
