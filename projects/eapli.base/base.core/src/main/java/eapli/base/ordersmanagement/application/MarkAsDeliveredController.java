package eapli.base.ordersmanagement.application;

import eapli.base.ordersmanagement.domain.Order_;

public interface MarkAsDeliveredController {

    Iterable<Order_> allDispatchedOrders();

    void deliveredOrder(Order_ order);

}
