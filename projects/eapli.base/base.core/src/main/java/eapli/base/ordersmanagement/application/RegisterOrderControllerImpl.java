package eapli.base.ordersmanagement.application;

import com.sun.istack.NotNull;
import eapli.base.customerusermanagement.domain.Address;
import eapli.base.customerusermanagement.domain.VatCode;
import eapli.base.customerusermanagement.repositories.CustomerRepository;
import eapli.base.ordersmanagement.domain.*;
import eapli.base.ordersmanagement.repositories.OrderRepository;
import eapli.base.productmanagement.domain.AlphaNumericCode;
import eapli.base.productmanagement.domain.Product;
import eapli.base.productmanagement.repositories.ProductRepository;
import eapli.base.usermanagement.domain.BaseRoles;
import eapli.framework.application.UseCaseController;
import eapli.framework.infrastructure.authz.application.AuthorizationService;

import java.util.HashSet;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Set;
import java.util.Date;


/**
 *
 * A products order has an automatically set numeric identifier and the datetime it has been registered
 * in the system. It mandatorily refers to a single customer and records the typed/selected billing and
 * delivering address, the ordered products and its quantities and unitary prices as well as the order total
 * amount (before and after taxes). In this respect, it is worth highlighting that products information
 * (e.g.: prices but sometimes also descriptions) change over time. Such changes cannot affect the past
 * orders original information. Besides that, an order also comprehends:
 *
 * • The shipment method selected (e.g.: Standard, Blue, Green) and the respective cost, which varies
 * in accordance with the selected method, the volume of the products packages and its weight.
 *
 * • the (automatic) payment method (e.g.: paypal) selected to pay the order should also be recorded
 * together with the payment confirmation received from the respective payment service. Notice
 * that, there is no need/intent to record the concrete payment data used/typed by the customer
 * (e.g.: the paypal credentials). It is enough knowing the method and the confirmation received by
 * the respective payment service.
 *
 * While developing the system prototype, these two issues (i.e.: shipment and payment) must be
 * considered. Although, since both are not perceived as representing a risk to the project, the shipment
 * cost computation, and the connections to external systems (e.g.: carriers and payment services)
 * should be mock.
 *
 * At least for some organizations, it is also important that the system allows sales department clerks to
 * create orders on behalf of a given customer. Thus, the system should collect the required information
 * to distinguish between those orders registered directly by the customer and the ones registered by a
 * clerk. In the latter case, due trackability concerns, the system should be able (at least) to identity the
 * respective clerk.
 *
 * Another relevant aspect the system needs to track properly concerns the several states that every
 * order goes by since it is registered in the system to the moment it is received by the customers. Some
 * possible states that can be considered are: (i) registered, (ii) payment pending, (iii) to be prepared
 * (waiting for an initial warehouse action), (iv) being prepared on the warehouse, (v) ready for
 * packaging; (vi) ready for carrier dispatching, (vii) dispatched, (viii) delivered by carrier, (ix) received by
 * customer. Notice that all relevant states might not be listed and some of the listed states might be
 * redundant and/or not necessary by some reason. As so, this matter should be carefully analyzed.
 *
 *
 */

@UseCaseController
public class RegisterOrderControllerImpl implements RegisterOrderController{

    private final OrderRepository orderRepository;

    private final CustomerRepository customerRepository;

    private final ProductRepository productRepository;

    private final AuthorizationService authz;

    public RegisterOrderControllerImpl(@NotNull OrderRepository orderRepository, @NotNull CustomerRepository customerRepository, @NotNull ProductRepository productRepository, @NotNull AuthorizationService authzService) {
        this.productRepository = productRepository;
        if(orderRepository == null)
            throw new IllegalArgumentException("OrderRepository cannot be null");
        if(customerRepository == null)
            throw new IllegalArgumentException("CustomerRepository cannot be null");
        if(authzService == null)
            throw new IllegalArgumentException("AuthorizationService cannot be null");
        this.orderRepository = orderRepository;
        this.customerRepository = customerRepository;
        this.authz = authzService;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Order_ registerOrder(Map<String, Long> orderLines, String customerId, ShipmentMethod shipmentMethod, PaymentMethod paymentMethod){
        this.authz.ensureAuthenticatedUserHasAnyOf(BaseRoles.POWER_USER, BaseRoles.ADMIN, BaseRoles.SALES_CLERK);
        VatCode customerVatID = VatCode.valueOf(customerId);
        Address deliveryAddress = customerDeliveryAddress(customerVatID);
        Order_ order = new Order_(customerVatID, shipmentMethod, paymentMethod, deliveryAddress, new Date());
        Set<OrderLine> linesToOrder = linesToOrder(orderLines, order);
        order.setOrderLines(linesToOrder);
       return this.orderRepository.save(order);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Order_ confirmPayment(byte[] paymentConfirmation, Long orderId) {
        PaymentConfirmation confirmation = PaymentConfirmation.valueOf(paymentConfirmation);
        return confirmPayment(confirmation, orderId);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Order_ confirmPayment(PaymentConfirmation paymentConfirmation, Long orderId) {
        this.authz.ensureAuthenticatedUserHasAnyOf(BaseRoles.POWER_USER, BaseRoles.ADMIN, BaseRoles.SALES_CLERK);
        if(orderRepository.containsOfIdentity(orderId)){
            Order_ currentOrder = orderRepository.ofIdentity(orderId).get();
            boolean paymentConfirmed = currentOrder.confirmPayment(paymentConfirmation);
            if (paymentConfirmed){
                currentOrder.changeOrderStatus(OrderStatus.REGISTERED);
                return orderRepository.save(currentOrder);
            }else{
                throw new IllegalArgumentException("The payment confirmation can't be null, the order can't be registered.");
            }
        }
        return null;
    }

    private Address customerDeliveryAddress(VatCode customerId){
        try {
            return customerRepository.ofIdentity(customerId).get().deliveryAddress();
        }catch (NoSuchElementException e){
            throw new IllegalArgumentException("This Customer is not registered.");
        }
    }

    private Set<OrderLine> linesToOrder(Map<String, Long> orderLines, Order_ order){
        Set<OrderLine> orderLineSet = new HashSet<>();
        for(Map.Entry<String,Long> rawOrderLine : orderLines.entrySet()){
            Product productToOrder = productToOrder(rawOrderLine.getKey());
            Long orderQuantity = rawOrderLine.getValue();
            OrderLine orderLine = new OrderLine(productToOrder.identity(), productToOrder.shortDescription(),
                                                orderQuantity, productToOrder.price(), order);
            orderLineSet.add(orderLine);
        }
        return orderLineSet;
    }

    private Product productToOrder(String productId) {
        try {
            Product product = productRepository.ofIdentity(AlphaNumericCode.valueOf(productId)).get();
            if(product.isActive()) return product;
            else throw new IllegalStateException("This Product is not Active.");
        } catch (NoSuchElementException e){
            throw new IllegalArgumentException("This Product is not registered.");
        }
    }


}
