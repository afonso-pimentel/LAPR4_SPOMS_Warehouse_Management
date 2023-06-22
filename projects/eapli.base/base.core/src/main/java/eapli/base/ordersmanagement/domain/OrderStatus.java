package eapli.base.ordersmanagement.domain;

public enum OrderStatus {
    PAYMENT_PENDING,
    REGISTERED,
    IN_PREPARATION,
    READY_FOR_PACKAGING,
    READY_FOR_DISPATCH,
    DISPATCHED,
    DELIVERED,
    RECEIVED;
}
