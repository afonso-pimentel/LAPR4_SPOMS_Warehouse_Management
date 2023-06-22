package eapli.base.ordersmanagement.application;

import eapli.base.ordersmanagement.domain.*;

import java.util.Map;

public interface RegisterOrderController {

    /**
     * Constructor of an Order with Raw Information.
     *
     * @param orderLines
     * @param customerId
     * @param shipmentMethod
     * @param paymentMethod
     * @return
     */
    Order_ registerOrder(Map<String, Long> orderLines, String customerId, ShipmentMethod shipmentMethod, PaymentMethod paymentMethod);


    /**
     * @param paymentConfirmation
     * @param orderId
     * @return
     */
    Order_ confirmPayment(byte[] paymentConfirmation, Long orderId);

    /**
     *
     * @param paymentConfirmation
     * @param orderId
     * @return
     */
    Order_ confirmPayment(PaymentConfirmation paymentConfirmation, Long orderId);
}
