package eapli.base.ordersmanagement.application.algorithms;

/**
 * @inheritDoc
 *
 */
public class AlgorithmsStrategyFactoryImpl implements AlgorithmsStrategyFactory{
    /**
     * @inheritDoc
     *
     */
    @Override
    public AlgorithmsStrategy GetStrategy(AlgorithmsStrategyList alg) {
        switch (alg){
            case ORDERS_FIFO:
                return new OrderFifoAlgorithmStrategy();
            default:
                return null;
        }
    }
}