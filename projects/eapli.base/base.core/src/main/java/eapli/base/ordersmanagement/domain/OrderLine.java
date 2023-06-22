package eapli.base.ordersmanagement.domain;

import com.sun.istack.NotNull;
import eapli.base.TaxValues;
import eapli.base.productmanagement.domain.AlphaNumericCode;
import eapli.base.productmanagement.domain.ShortDescription;
import eapli.framework.domain.model.AggregateRoot;
import eapli.framework.general.domain.model.Money;

import javax.persistence.*;
import java.util.Objects;

@Entity
public class OrderLine implements AggregateRoot<Long> {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long orderlineId;
    @AttributeOverride(name = "code", column = @Column(name = "productId"))
    private AlphaNumericCode productId;
    @AttributeOverride(name = "value", column = @Column(name = "shortDescription"))
    private ShortDescription shortDescription;
    @AttributeOverride(name = "quantity", column = @Column(name = "quantity"))
    private Long quantity;
    @AttributeOverride(name = "amount", column = @Column(name = "unitPrice"))
    @AttributeOverride(name = "currency", column = @Column(name = "unit_price_currency"))
    private Money unitPrice;
    @AttributeOverride(name = "amount", column = @Column(name = "totalPriceBeforeTax"))
    @AttributeOverride(name = "currency", column = @Column(name = "price_before_tax_currency"))
    private Money totalPriceBeforeTax;
    @ManyToOne(cascade = {CascadeType.ALL})
    private Order_ order;

    /**
     * Constructor for OrderLines where we get only the minimum info and the rest is calculated.
     * @param productId
     * @param shortDescription
     * @param quantity
     * @param unitPrice
     */
    public OrderLine(@NotNull final AlphaNumericCode productId,@NotNull final ShortDescription shortDescription,@NotNull
                     final Long quantity,@NotNull final Money unitPrice,@NotNull final Order_ order){
        if(productId == null){
            throw new IllegalArgumentException("Product ID cannot be null.");
        }
        if(shortDescription == null){
            throw new IllegalArgumentException("Short Description cannot be null.");
        }
        if(quantity == null){
            throw new IllegalArgumentException("Quantity cannot be null.");
        }
        if(unitPrice == null){
            throw new IllegalArgumentException("Unit Price cannot be null.");
        }
        if(order == null){
            throw new IllegalArgumentException("Order cannot be null.");
        }
        this.productId = productId;
        this.shortDescription = shortDescription;
        this.quantity = quantity;
        this.unitPrice = unitPrice;
        this.totalPriceBeforeTax = unitPrice.multiply(quantity.doubleValue());
        this.order = order;
    }

    protected OrderLine(){
        //for ORM
        productId = null;
        shortDescription = null;
        quantity = null;
        unitPrice = null;
        totalPriceBeforeTax = null;
        order = null;
    }

    public AlphaNumericCode productId() { return this.productId; }
    public ShortDescription shortDescription() {return this.shortDescription; }
    public Long quantity() { return this.quantity; }
    public Money unitPrice() { return this.unitPrice; }

    public Money totalPriceBeforeTax() { return this.totalPriceBeforeTax; }

    public Money totalPriceAfterTax() { return this.totalPriceBeforeTax.multiply(TaxValues.IVA.getValue()); }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        OrderLine orderLine = (OrderLine) o;
        return productId.equals(orderLine.productId)
                && shortDescription.equals(orderLine.shortDescription)
                && quantity.equals(orderLine.quantity)
                && unitPrice.equals(orderLine.unitPrice)
                && totalPriceBeforeTax.equals(orderLine.totalPriceBeforeTax);
    }



    @Override
    public String toString() {
        return  "# " + productId.toString() +
                " || " + shortDescription.toString() +
                " || " + quantity.toString() +
                "ud. || " + unitPrice.toString() +
                " || " + totalPriceBeforeTax.toString();
    }

    @Override
    public int hashCode() {
        return Objects.hash(productId, shortDescription, quantity, unitPrice, totalPriceBeforeTax);
    }

    @Override
    public boolean sameAs(Object other) {
        return false;
    }

    @Override
    public int compareTo(Long other) {
        return AggregateRoot.super.compareTo(other);
    }

    @Override
    public Long identity() {
        return this.orderlineId;
    }

    @Override
    public boolean hasIdentity(Long id) {
        return AggregateRoot.super.hasIdentity(id);
    }
}
