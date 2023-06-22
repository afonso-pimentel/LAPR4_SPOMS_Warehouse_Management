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

package eapli.base.surveysmanagement.domain;

import java.util.ArrayList;

/**
 * Enumeration for all the currently available survey rules
 */
public enum SurveyRules {
    /**
     * Rule for a specific product bought on a given date period
     */
    PERIOD_PRODUCT(1),

    /**
     * Rule for products from a specific brand bought on a given date period
     */
    PERIOD_BRAND(2),

    /**
     * Rule for products from a specific category bought on a given date period
     */
    PERIOD_CATEGORY(3),

    /**
     * Rule for customers with an age that lies on a specific age period
     */
    PERIOD_AGE(4),

    /**
     * Rule for customers with a specific gender
     */
    SCALAR_GENDER(5),

    /**
     * Rule for all customers
     */
    SCALAR_ALL_CUSTOMERS(6);

    private int rule;

    /**
     * Initializes a new instance of SurveyRules
     * @param rule rule
     */
    SurveyRules(int rule) {
        this.rule = rule;
    }

    /**
     * Returns the rule
     * @return
     */
    public int rule() {
        return rule;
    }

    /**
     * Returns a list of all the available rules
     * @return List of Survey Rules
     */
    public static Iterable<SurveyRules> availableRules(){
        var listOfRules = new ArrayList<SurveyRules>();
        listOfRules.add(PERIOD_PRODUCT);
        listOfRules.add(PERIOD_BRAND);
        listOfRules.add(PERIOD_CATEGORY);
        listOfRules.add(PERIOD_AGE);
        listOfRules.add(SCALAR_GENDER);
        listOfRules.add(SCALAR_ALL_CUSTOMERS);

        return listOfRules;
    }
}
