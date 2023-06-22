package eapli.base.ordersmanagement.application;

import com.sun.istack.NotNull;
import eapli.base.ordersmanagement.domain.OrderStatus;
import eapli.base.ordersmanagement.domain.Order_;
import eapli.base.usermanagement.domain.BaseRoles;
import eapli.base.warehousemanagement.application.AgvService;
import eapli.base.warehousemanagement.domain.Agv;
import eapli.base.warehousemanagement.domain.AgvStatus;
import eapli.framework.infrastructure.authz.application.AuthorizationService;

public class AssignOrderToAgvControllerImpl implements AssignOrderToAgvController {
    private final AuthorizationService authz;

    private final ListOrderService listOrderService;
    private final AgvService agvService;

    private final ChangeOrderStatusService changeOrderStatusService;

    public AssignOrderToAgvControllerImpl(@NotNull ListOrderService listOrderService, @NotNull ChangeOrderStatusService changeOrderStatusService, @NotNull AuthorizationService authz, @NotNull AgvService agvService) {
        if (authz == null)
            throw new IllegalArgumentException("AuthorizationService cannot be null");
        if (listOrderService == null) {
            throw new IllegalArgumentException("ListOrderService cannot be null");
        }
        if (changeOrderStatusService == null) {
            throw new IllegalArgumentException("ChangeOrderStatusService cannot be null");
        }
        if (agvService == null) {
            throw new IllegalArgumentException("AgvService cannot be null");
        }
        this.authz = authz;
        this.listOrderService = listOrderService;
        this.changeOrderStatusService = changeOrderStatusService;
        this.agvService = agvService;
    }

    public Iterable<Order_> allRegisteredOrders() {
        this.authz.ensureAuthenticatedUserHasAnyOf(BaseRoles.POWER_USER, BaseRoles.ADMIN, BaseRoles.WAREHOUSE_EMPLOYEE);
        return listOrderService.allRegisteredOrders();
    }
    public Iterable<Agv> availableAgvs() {
        this.authz.ensureAuthenticatedUserHasAnyOf(BaseRoles.POWER_USER, BaseRoles.ADMIN, BaseRoles.WAREHOUSE_EMPLOYEE);
        return agvService.availableAgvs();
    }

    public void assignOrderToAgv(Order_ order, Agv avg) {
        this.authz.ensureAuthenticatedUserHasAnyOf(BaseRoles.POWER_USER, BaseRoles.ADMIN, BaseRoles.WAREHOUSE_EMPLOYEE);
        changeOrderStatusService.changeOrderStatus(order, OrderStatus.IN_PREPARATION);
        agvService.changeAgvStatus(avg, AgvStatus.WORKING);

    }
}