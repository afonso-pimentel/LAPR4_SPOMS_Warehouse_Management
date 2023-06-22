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

package eapli.base.surveysmanagement.application;

import eapli.base.customerusermanagement.domain.Customer;
import eapli.base.productmanagement.domain.AlphaNumericCode;
import eapli.base.surveysmanagement.application.strategy.SurveyRuleStrategy;
import eapli.base.surveysmanagement.application.strategy.SurveyRuleStrategyFactory;
import eapli.base.surveysmanagement.domain.Survey;
import eapli.base.surveysmanagement.domain.SurveyException;
import eapli.base.surveysmanagement.domain.SurveyRule;
import eapli.base.surveysmanagement.repositories.SurveyRepository;
import eapli.framework.domain.repositories.IntegrityViolationException;
import eapli.framework.general.domain.model.Description;
import java.util.*;

/**
 * Implementation for RegisterSurveyService
 */
public class RegisterSurveyServiceImpl implements RegisterSurveyService {
    private final SurveyRepository surveyRepository;
    private final SurveyRuleStrategyFactory surveyRuleStrategyFactory;

    /**
     * Initializes a new instance of RegisterSurveyServiceImpl
     * @param surveyRepository SurveyRepository
     * @param surveyRuleStrategyFactory SurveyRuleStrategyFactory
     */
    public RegisterSurveyServiceImpl(SurveyRepository surveyRepository, SurveyRuleStrategyFactory surveyRuleStrategyFactory) {
        if(surveyRepository == null)
            throw new IllegalArgumentException("SurveyRepository cannot be null");

        if(surveyRuleStrategyFactory == null)
            throw new IllegalArgumentException("SurveyRuleStrategyFactory cannot be null");

        this.surveyRepository = surveyRepository;
        this.surveyRuleStrategyFactory = surveyRuleStrategyFactory;
    }

    /**
     * @inheritDoc
     */
    @Override
    public Survey addSurvey(String surveyCode, String description, Date startDate, Date endDate, byte[] questionnaireFile, Iterable<SurveyRule> rulesToBeApplied) throws SurveyException {
        Optional<Survey> existentSurvey = surveyRepository.find(AlphaNumericCode.valueOf(surveyCode));

        if(existentSurvey.isPresent())
            throw new IntegrityViolationException("A Survey is already registered with the code " + surveyCode);

        var targetAudience = new HashSet<Customer>();

        for(SurveyRule surveyRule : rulesToBeApplied){
            SurveyRuleStrategy strategy = surveyRuleStrategyFactory.getStrategy(surveyRule.rule());

            if(strategy != null){
                Iterable<Customer> ruleTargetAudience = strategy.performStrategy(surveyRule);
                ruleTargetAudience.forEach(targetAudience::add);
            }
        }

        if(targetAudience.size() == 0)
            throw new SurveyException("Invalid survey. No target audience was found for the survey");

        var survey = new Survey(AlphaNumericCode.valueOf(surveyCode), Description.valueOf(description),
                        startDate, endDate,
                        questionnaireFile, targetAudience);

        surveyRepository.save(survey);

        return surveyRepository.find(survey.surveyCode()).get();
    }
}
