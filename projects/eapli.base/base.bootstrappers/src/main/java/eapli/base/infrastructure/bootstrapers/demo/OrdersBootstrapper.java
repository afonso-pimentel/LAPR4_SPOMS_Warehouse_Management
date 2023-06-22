package eapli.base.infrastructure.bootstrapers.demo;

import eapli.base.customerusermanagement.domain.Address;
import eapli.base.customerusermanagement.domain.Customer;
import eapli.base.customerusermanagement.domain.VatCode;
import eapli.base.customerusermanagement.repositories.CustomerRepository;
import eapli.base.infrastructure.persistence.PersistenceContext;
import eapli.base.ordersmanagement.domain.*;
import eapli.base.ordersmanagement.repositories.OrderRepository;
import eapli.base.productmanagement.domain.AlphaNumericCode;
import eapli.base.productmanagement.domain.Product;
import eapli.base.productmanagement.domain.ShortDescription;
import eapli.base.productmanagement.repositories.ProductRepository;
import eapli.framework.actions.Action;
import eapli.framework.domain.repositories.ConcurrencyException;
import eapli.framework.domain.repositories.IntegrityViolationException;
import eapli.framework.general.domain.model.Money;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class OrdersBootstrapper implements Action {

    private static final Logger LOGGER = LoggerFactory.getLogger(
            AgvBootstrapper.class);

    private final OrderRepository ordersRepository;

    private final ProductRepository productRepository;

    private final CustomerRepository customerRepository;

    public OrdersBootstrapper(){
        ordersRepository = PersistenceContext.repositories().orders();
        productRepository = PersistenceContext.repositories().products();
        customerRepository = PersistenceContext.repositories().customers();
    }

    @Override
    public boolean execute() {
        registerOrders(ShipmentMethod.DHL, PaymentMethod.CREDIT_CARD, new Date(), OrderStatus.REGISTERED);
        registerOrders(ShipmentMethod.CTT, PaymentMethod.CREDIT_CARD, new Date(), OrderStatus.REGISTERED);
        registerOrders(ShipmentMethod.UPS, PaymentMethod.PAYPAL, new Date(), OrderStatus.READY_FOR_DISPATCH);
        registerOrders(ShipmentMethod.CTT, PaymentMethod.MBWAY, new Date(), OrderStatus.DISPATCHED);
        return true;
    }
    private boolean senario = false;
    private void registerOrders(ShipmentMethod shipmentMethod, PaymentMethod paymentMethod, Date orderDate, OrderStatus orderStatus){
        try {
            Customer customer = customerRepository.ofIdentity(VatCode.valueOf("123456789")).get();

            var order = new Order_(customer.identity(), shipmentMethod, paymentMethod, customer.deliveryAddress(), orderDate);


            OrderLine orderLineOne = new OrderLine(AlphaNumericCode.valueOf("LIONT"), ShortDescription.valueOf("T-Shirt"), 2L, Money.euros(20), order);
            OrderLine orderLineTwo = new OrderLine(AlphaNumericCode.valueOf("LIONJ"),ShortDescription.valueOf("Jeans"), 3L, Money.euros(20), order);
            OrderLine orderLineThree = new OrderLine(AlphaNumericCode.valueOf("LIONH"),ShortDescription.valueOf("Hoddie"), 2L, Money.euros(20), order);

            Set<OrderLine> orderLines = new HashSet<>();

            if (senario) {
                orderLines.add(orderLineOne);
                orderLines.add(orderLineTwo);
            }else   {
                orderLines.add(orderLineTwo);
                orderLines.add(orderLineThree);
            }
            senario = !senario;

            order.setOrderLines(orderLines);
            order.changeOrderStatus(orderStatus);
            ordersRepository.save(order);
        } catch (final ConcurrencyException | IntegrityViolationException e) {
            LOGGER.warn("Assuming {} already exists (activate trace log for details)");
            LOGGER.trace("Assuming existing record", e);
        }
    }
}
