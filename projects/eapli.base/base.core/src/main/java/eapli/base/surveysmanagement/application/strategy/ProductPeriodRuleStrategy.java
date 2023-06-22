/*
 * Copyright (c) 2021-2022 the original author or authors.
 *
 * MIT License
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

package eapli.base.surveysmanagement.application.strategy;

import eapli.base.customerusermanagement.domain.Customer;
import eapli.base.customerusermanagement.repositories.CustomerRepository;
import eapli.base.ordersmanagement.domain.Order_;
import eapli.base.ordersmanagement.repositories.OrderRepository;
import eapli.base.productmanagement.domain.AlphaNumericCode;
import eapli.base.surveysmanagement.domain.SurveyRule;
import eapli.base.surveysmanagement.domain.SurveyRules;
import java.util.ArrayList;
import java.util.Optional;

/**
 * Strategy for the product period rule
 */
public class ProductPeriodRuleStrategy implements SurveyRuleStrategy{
    private final OrderRepository orderRepository;
    private final CustomerRepository customerRepository;

    /**
     * Initializes a new instance of ProductPeriodRuleStrategy
     * @param orderRepository OrderRepository
     * @param customerRepository CustomerRepository
     */
    public ProductPeriodRuleStrategy(OrderRepository orderRepository, CustomerRepository customerRepository) {
        if(orderRepository == null)
            throw new IllegalArgumentException("OrderRepository cannot be null");

        if(customerRepository == null)
            throw new IllegalArgumentException("CustomerRepository cannot be null");

        this.orderRepository = orderRepository;
        this.customerRepository = customerRepository;
    }

    /**
     * @inheritDoc
     *
     */
    @Override
    public Iterable<Customer> performStrategy(SurveyRule rule) {
        if(rule == null)
            throw new IllegalArgumentException("SurveyRule cannot be null");

        if(rule.rule() != SurveyRules.PERIOD_PRODUCT)
            throw new IllegalArgumentException("Invalid SurveyRule for current strategy");

        Iterable<Order_> orders = orderRepository.findOrdersWithSpecificProduct(AlphaNumericCode.valueOf(rule.value()), rule.startDate(), rule.endDate());

        var validCustomers = new ArrayList<Customer>();

        for(Order_ order : orders){
            Optional<Customer> customer = customerRepository.findByVatCode(order.customerId());

            if(customer.isPresent() && !validCustomers.contains(customer.get()))
                validCustomers.add(customer.get());
        }

        return validCustomers;
    }
}
