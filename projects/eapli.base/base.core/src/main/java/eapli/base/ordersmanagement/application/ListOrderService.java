package eapli.base.ordersmanagement.application;

import eapli.base.customerusermanagement.domain.VatCode;
import eapli.base.customerusermanagement.repositories.CustomerRepository;
import eapli.base.infrastructure.persistence.PersistenceContext;
import eapli.base.ordersmanagement.domain.OrderStatus;
import eapli.base.ordersmanagement.domain.Order_;
import eapli.base.ordersmanagement.repositories.OrderRepository;
import eapli.base.usermanagement.domain.BaseRoles;
import eapli.framework.infrastructure.authz.application.AuthorizationService;
import eapli.framework.infrastructure.authz.application.AuthzRegistry;

public class ListOrderService {
    private final AuthorizationService authz;
    private final OrderRepository orderRepository;
    private final CustomerRepository customerRepository;

    public ListOrderService(){
        authz = AuthzRegistry.authorizationService();
        orderRepository = PersistenceContext.repositories().orders();
        customerRepository = PersistenceContext.repositories().customers();
    }

    public ListOrderService(AuthorizationService authz, OrderRepository orderRepository, CustomerRepository customerRepository) {
        this.authz = authz;
        this.orderRepository = orderRepository;
        this.customerRepository = customerRepository;
    }

    public ListOrderService(AuthorizationService authz, OrderRepository orderRepository) {
        this.authz = authz;
        this.orderRepository = orderRepository;
        this.customerRepository = null;
    }


    public ListOrderService(OrderRepository orderRepository, CustomerRepository customerRepository){
        this.orderRepository = orderRepository;
        this.customerRepository = customerRepository;
        this.authz = null;
    }

    public Iterable<Order_> allOrders(VatCode customerID){
        //authz.ensureAuthenticatedUserHasAnyOf(BaseRoles.POWER_USER, BaseRoles.ADMIN, BaseRoles.SALES_CLERK);
        if(customerRepository.containsOfIdentity(customerID)){
            return orderRepository.findCustomerOrders(customerID);
        }
        throw new IllegalArgumentException("This customer is not registered in the system.");
    }

    public Iterable<Order_> allPreparedOrders(){
        return allOrdersByStatus(OrderStatus.READY_FOR_DISPATCH);
    }

    public Iterable<Order_> allRegisteredOrders(){
        return allOrdersByStatus(OrderStatus.REGISTERED);
    }

    public Iterable<Order_> allDispatchedOrders(){
        return allOrdersByStatus(OrderStatus.DISPATCHED);
    }

    /**
     * It returns all of the orders with a given status
     * @param orderStatus
     * @return
     */
    public Iterable<Order_> allOrdersByStatus(OrderStatus orderStatus){
        if(authz != null){
            authz.ensureAuthenticatedUserHasAnyOf(BaseRoles.POWER_USER, BaseRoles.ADMIN, BaseRoles.WAREHOUSE_EMPLOYEE);
        }

        return orderRepository.findOrdersByStatus(orderStatus);
    }
}
