package eapli.base.ordersmanagement.application;

import com.sun.istack.NotNull;
import eapli.base.ordersmanagement.domain.OrderStatus;
import eapli.base.ordersmanagement.domain.Order_;
import eapli.base.ordersmanagement.repositories.OrderRepository;
import eapli.base.usermanagement.domain.BaseRoles;
import eapli.framework.application.UseCaseController;
import eapli.framework.infrastructure.authz.application.AuthorizationService;

@UseCaseController
public class DispatchOrderControllerImpl implements DispatchOrderController {

    private final OrderRepository orderRepository;

    private final AuthorizationService authz;

    private final ListOrderService listOrderService;

    private final ChangeOrderStatusService changeOrderStatusService;

    public DispatchOrderControllerImpl(@NotNull OrderRepository orderRepository, @NotNull ListOrderService listOrderService, @NotNull ChangeOrderStatusService changeOrderStatusService, @NotNull AuthorizationService authz) {
        if(orderRepository == null)
            throw new IllegalArgumentException("OrderRepository cannot be null");
        if(authz == null)
            throw new IllegalArgumentException("AuthorizationService cannot be null");
        if(listOrderService == null){
            throw new IllegalArgumentException("ListOrderService cannot be null");
        }
        if(changeOrderStatusService == null){
            throw new IllegalArgumentException("ChangeOrderStatusService cannot be null");
        }
        this.orderRepository = orderRepository;
        this.authz = authz;
        this.listOrderService = listOrderService;
        this.changeOrderStatusService = changeOrderStatusService;
    }

    public Iterable<Order_> allPreparedOrders(){
        this.authz.ensureAuthenticatedUserHasAnyOf(BaseRoles.POWER_USER, BaseRoles.ADMIN, BaseRoles.WAREHOUSE_EMPLOYEE);
        return listOrderService.allPreparedOrders(); }

    public void dispatchOrder(Order_ order){
        this.authz.ensureAuthenticatedUserHasAnyOf(BaseRoles.POWER_USER, BaseRoles.ADMIN, BaseRoles.WAREHOUSE_EMPLOYEE);
        changeOrderStatusService.changeOrderStatus(order, OrderStatus.DISPATCHED);
    }
}
