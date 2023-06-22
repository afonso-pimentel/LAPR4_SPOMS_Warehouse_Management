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

import eapli.framework.time.domain.model.TimeInterval;

import java.util.Date;

/**
 * SurveyRule
 */
public class SurveyRule {
    private final SurveyRules rule;
    private final String value;
    private final String value2;

    private final Date startDate;

    private final Date endDate;

    /**
     * Initializes a new instance of SurveyRuleDTO
     * @param rule Rule
     * @param value Value for Rule
     */
    public SurveyRule(SurveyRules rule, String value, String value2) {
        if(rule == null)
            throw new IllegalArgumentException("SurveyRuleDTO cannot be null");

        if(value == null || value == "")
            throw new IllegalArgumentException("Value cannot be null");

        this.rule = rule;
        this.value = value;
        this.value2 = value2;
        this.startDate = null;
        this.endDate = null;
    }

    /**
     * Initializes a new instance of SurveyRuleDTO
     * @param rule Rule
     * @param value Value for Rule
     * @param startDate Start date
     * @param endDate End date
     */
    public SurveyRule(SurveyRules rule, String value, Date startDate, Date endDate) {
        if(rule == null)
            throw new IllegalArgumentException("SurveyRuleDTO cannot be null");

        if(value == null || value == "")
            throw new IllegalArgumentException("Value cannot be null");

        if(startDate == null)
            throw new IllegalArgumentException("StartDate cannot be null");

        if(endDate == null)
            throw new IllegalArgumentException("EndDate cannot be null");

        this.rule = rule;
        this.value = value;
        this.startDate = startDate;
        this.endDate = endDate;
        this.value2 = null;
    }

    /**
     * Initializes a new instance of SurveyRuleDTO
     * @param rule Rule
     * @param value Value for Rule
     * @param value2 Value 2 for rule
     * @param startDate Start date
     * @param endDate End date
     */
    public SurveyRule(SurveyRules rule, String value, String value2, Date startDate, Date endDate) {
        if(rule == null)
            throw new IllegalArgumentException("SurveyRuleDTO cannot be null");

        if(value == null || value == "")
            throw new IllegalArgumentException("Value cannot be null");

        this.rule = rule;
        this.value = value;
        this.value2 = value2;
        this.startDate = startDate;
        this.endDate = endDate;
    }

    /**
     * Returns the rule type
     * @return SurveyRule
     */
    public SurveyRules rule() {
        return rule;
    }

    /**
     * Returns the value to be applied to the rule
     * @return String
     */
    public String value() {
        return value;
    }

    /**
     * Returns the value2 to be applied to the rule
     * @return Value2
     */
    public String value2(){
        return value2;
    }

    /**
     * Returns the start date for the rule
     * @return
     */
    public Date startDate(){
        return startDate;
    }

    /**
     * Returns the end date for the rule
     * @return
     */
    public Date endDate(){
        return endDate;
    }
}
