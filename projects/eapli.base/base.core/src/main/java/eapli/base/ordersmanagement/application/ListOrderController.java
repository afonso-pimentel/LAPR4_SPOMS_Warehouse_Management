package eapli.base.ordersmanagement.application;
import eapli.base.customerusermanagement.domain.VatCode;
import eapli.base.ordersmanagement.domain.Order_;

public interface ListOrderController {
    Iterable<Order_> customerOrders(VatCode customerID);

    Iterable<Order_> preparedOrders();
}
