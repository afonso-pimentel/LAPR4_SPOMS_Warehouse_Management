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

package eapli.base.app.backoffice.console.presentation.survey;

import eapli.base.customerusermanagement.domain.Customer;
import eapli.base.surveysmanagement.domain.Survey;
import eapli.framework.visitor.Visitor;

/**
 * Implementation of visitor for Survey
 */
public class SurveyPrinter implements Visitor<Survey> {

    /**
     * @inheritDoc
     *
     */
    @Override
    public void visit(Survey visitee) {
        System.out.println("Survey code: " + visitee.surveyCode().code());
        System.out.println("Survey description: " + visitee.description().toString());
        System.out.println("Start date: " + visitee.startDate().toString());
        System.out.println("End date: " + visitee.endDate().toString());

        System.out.println();
        System.out.println("Target audience: ");


        for(Customer customer : visitee.targetAudience()){
            System.out.println();
            System.out.println("___________CUSTOMER_______________");
            System.out.println("Name: " + customer.name().toString());
            System.out.println("Gender: " + customer.gender().toString());
            System.out.println("Email: " + customer.email().toString());
            System.out.println("VAT: " + customer.identity().toString());
            System.out.println("___________________________________");
        }
    }
}
