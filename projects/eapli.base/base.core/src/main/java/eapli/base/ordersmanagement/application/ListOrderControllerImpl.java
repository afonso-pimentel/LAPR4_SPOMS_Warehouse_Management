package eapli.base.ordersmanagement.application;

import eapli.base.customerusermanagement.domain.VatCode;
import eapli.base.customerusermanagement.repositories.CustomerRepository;
import eapli.base.ordersmanagement.domain.OrderStatus;
import eapli.base.ordersmanagement.domain.Order_;
import eapli.base.ordersmanagement.repositories.OrderRepository;
import eapli.framework.infrastructure.authz.application.AuthorizationService;

public class ListOrderControllerImpl implements ListOrderController{

    private final OrderRepository orderRepository;

    private final CustomerRepository customerRepository;

    private final ListOrderService orderService;

    private final AuthorizationService authz;


    public ListOrderControllerImpl(OrderRepository orderRepository, CustomerRepository customerRepository, AuthorizationService authz) {
        if(orderRepository == null)
            throw new IllegalArgumentException("OrderRepository cannot be null");
        if(customerRepository == null)
            throw new IllegalArgumentException("CustomerRepository cannot be null");
        if(authz == null)
            throw new IllegalArgumentException("AuthorizationService cannot be null");
        this.orderRepository = orderRepository;
        this.customerRepository = customerRepository;
        this.authz = authz;
        this.orderService = new ListOrderService();
    }

    public ListOrderControllerImpl(OrderRepository orderRepository, CustomerRepository customerRepository, AuthorizationService authz, ListOrderService listOrderService) {
        if(orderRepository == null)
            throw new IllegalArgumentException("OrderRepository cannot be null");
        if(customerRepository == null)
            throw new IllegalArgumentException("CustomerRepository cannot be null");
        if(authz == null)
            throw new IllegalArgumentException("AuthorizationService cannot be null");
        this.orderRepository = orderRepository;
        this.customerRepository = customerRepository;
        this.authz = authz;
        this.orderService = listOrderService;
    }

    public Iterable<Order_> customerOrders(VatCode customerID) {
        return orderService.allOrders(customerID);
    }

    public Iterable<Order_> preparedOrders(){
        return orderService.allPreparedOrders();
    }

}
