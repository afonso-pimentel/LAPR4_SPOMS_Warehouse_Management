package eapli.base.ordersmanagement.application.algorithms;

import com.fasterxml.jackson.core.JsonProcessingException;
import eapli.base.ordersmanagement.domain.Order_;

import java.util.List;

/**
 * Base contract for all the Algorithm strategies
 */
public interface AlgorithmsStrategy<T> {
    /**
     * Performs the strategy
     * @return T
     */
    T execute(List<Object> args);
}