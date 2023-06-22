package eapli.base.ordersmanagement.application.algorithms;

import eapli.base.infrastructure.persistence.PersistenceContext;
import eapli.base.ordersmanagement.domain.Order_;
import eapli.base.ordersmanagement.repositories.OrderRepository;

import java.util.List;
import java.util.Optional;

public class OrderFifoAlgorithmStrategy implements AlgorithmsStrategy{
    @Override
    public Optional<Order_> execute(List args) {
        OrderRepository orderRepo = PersistenceContext.repositories().orders();
        Iterable<Order_> orders = orderRepo.findOldestRegisteredOrders();
        return  orders.iterator().hasNext() ? Optional.of(orders.iterator().next()) : Optional.ofNullable((Order_) null);
    }
}
