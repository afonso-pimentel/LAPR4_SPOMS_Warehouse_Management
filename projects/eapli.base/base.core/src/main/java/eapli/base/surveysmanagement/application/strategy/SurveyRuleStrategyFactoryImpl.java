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

import eapli.base.infrastructure.persistence.PersistenceContext;
import eapli.base.surveysmanagement.domain.SurveyRules;

/**
 * Implementation for SurveyRuleStrategyFactory
 */
public class SurveyRuleStrategyFactoryImpl implements SurveyRuleStrategyFactory{

    /**
     * @inheritDoc
     *
     */
    @Override
    public SurveyRuleStrategy getStrategy(SurveyRules rule) {
        switch(rule){
            case PERIOD_PRODUCT:
                return new ProductPeriodRuleStrategy(PersistenceContext.repositories().orders(), PersistenceContext.repositories().customers());
            case PERIOD_BRAND:
                return new BrandPeriodRuleStrategy(PersistenceContext.repositories().orders(), PersistenceContext.repositories().customers());
            case PERIOD_CATEGORY:
                return new CategoryPeriodRuleStrategy(PersistenceContext.repositories().orders(), PersistenceContext.repositories().customers());
            case PERIOD_AGE:
                return new AgePeriodRuleStrategy(PersistenceContext.repositories().customers());
            case SCALAR_GENDER:
                return new GenderScalarRuleStrategy(PersistenceContext.repositories().customers());
            case SCALAR_ALL_CUSTOMERS:
                return new AllCustomersRuleStrategy(PersistenceContext.repositories().customers());
            default:
                return null;
        }
    }
}
