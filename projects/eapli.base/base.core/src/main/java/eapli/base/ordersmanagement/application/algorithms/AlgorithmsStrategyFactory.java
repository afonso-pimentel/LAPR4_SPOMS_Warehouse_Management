package eapli.base.ordersmanagement.application.algorithms;

/**
 * Factory for all the available Algorithm Strategies
 */
public interface AlgorithmsStrategyFactory {

    /**
     * Returns the correct AlgorithmsStrategy for the specified Algorithm
     * @param alg AlgorithmsStrategyList
     * @return AlgorithmsStrategy
     */
    AlgorithmsStrategy GetStrategy(AlgorithmsStrategyList alg);
}
