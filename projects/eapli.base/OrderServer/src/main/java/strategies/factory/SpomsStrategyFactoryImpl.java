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

package strategies.factory;

import eapli.base.infrastructure.persistence.PersistenceContext;
import enums.MessageCode;
import strategies.strategies.OrderStatusStrategy;
import strategies.strategies.ProductCatalogStrategy;
import strategies.strategies.QuestionnaireRequestStrategy;
import strategies.strategies.SpomsStrategy;
import strategies.strategies.SurveysRequestStrategy;
import strategies.strategies.*;

/**
 * @inheritDoc
 *
 */
public class SpomsStrategyFactoryImpl implements SpomsStrategyFactory{

    /**
     * @inheritDoc
     *
     */
    @Override
    public SpomsStrategy GetStrategy(MessageCode messageCode){
        switch(messageCode){
            case PRODUCT_CATALOG_REQUEST:
                 return new ProductCatalogStrategy();
            case ORDER_STATUS_REQUEST:
                 return new OrderStatusStrategy(PersistenceContext.repositories().orders());
            case CUSTOMER_SURVEYS_REQUEST:
                return new SurveysRequestStrategy(PersistenceContext.repositories().surveys(), PersistenceContext.repositories().answers());
            case QUESTIONNAIRE_REQUEST:
                return new QuestionnaireRequestStrategy(PersistenceContext.repositories().surveys());
            case ANSWER_REQUEST:
                return new AnswerResponseStrategy(PersistenceContext.repositories().answers());
            default:
                return null;
        }
    }
}
