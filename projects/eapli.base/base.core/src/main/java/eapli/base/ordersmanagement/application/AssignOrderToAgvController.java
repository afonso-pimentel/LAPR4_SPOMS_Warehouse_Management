package eapli.base.ordersmanagement.application;

import eapli.base.ordersmanagement.domain.Order_;
import eapli.base.warehousemanagement.domain.Agv;
public interface AssignOrderToAgvController {

    Iterable<Order_> allRegisteredOrders();
    Iterable<Agv> availableAgvs();

    void assignOrderToAgv(Order_ order, Agv avg);
}
