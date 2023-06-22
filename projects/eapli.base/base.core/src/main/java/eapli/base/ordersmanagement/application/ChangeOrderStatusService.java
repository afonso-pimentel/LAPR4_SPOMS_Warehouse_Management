package eapli.base.ordersmanagement.application;

import eapli.base.ordersmanagement.domain.OrderStatus;
import eapli.base.ordersmanagement.domain.Order_;
import eapli.base.ordersmanagement.repositories.OrderRepository;
import eapli.base.usermanagement.domain.BaseRoles;
import eapli.framework.infrastructure.authz.application.AuthorizationService;

public class ChangeOrderStatusService {
    private final AuthorizationService authz;
    private final OrderRepository orderRepository;
    private final boolean notIntern;

    public ChangeOrderStatusService(AuthorizationService authz, OrderRepository orderRepository) {
        this.authz = authz;
        this.orderRepository = orderRepository;
        notIntern = true;
    }
    public ChangeOrderStatusService(OrderRepository orderRepository) {
        this.authz = null;
        this.orderRepository = orderRepository;
        notIntern = false;
    }

    public void changeOrderStatus(Order_ order, OrderStatus orderStatus){
        if(notIntern)
            this.authz.ensureAuthenticatedUserHasAnyOf(BaseRoles.POWER_USER, BaseRoles.ADMIN, BaseRoles.WAREHOUSE_EMPLOYEE);

        if(orderRepository.contains(order)) {
            order.changeOrderStatus(orderStatus);
            orderRepository.save(order);
        }else{
            throw new IllegalArgumentException("This order is not registered.");
        }
    }
}
