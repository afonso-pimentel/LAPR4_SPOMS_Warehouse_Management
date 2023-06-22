package eapli.base.ordersmanagement.application;

import eapli.base.ordersmanagement.domain.Order_;

public interface DispatchOrderController {

    Iterable<Order_> allPreparedOrders();

    void dispatchOrder(Order_ order);

}
