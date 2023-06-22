package strategies;

import eapli.base.ordersmanagement.repositories.OrderRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import strategies.strategies.OrderStatusStrategy;
import static org.junit.jupiter.api.Assertions.assertThrows;

@ExtendWith(MockitoExtension.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class OrderStatusStrategyTests {
    @Mock
    private OrderRepository mockOrderRepository;

    private OrderStatusStrategy orderStatusStrategy;

    @BeforeEach
    public void createMocks() {
        MockitoAnnotations.openMocks(this);
        orderStatusStrategy = new OrderStatusStrategy(mockOrderRepository);
    }

    @Test
    public void performStrategy_NullOrderStatus_ShouldReturnIllegalArgumentException(){
        // Act & Assert
        assertThrows(IllegalArgumentException.class, () -> orderStatusStrategy.PerformStrategy(null));
    }

}