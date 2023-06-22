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

import eapli.base.surveysmanagement.domain.Answer;
import eapli.base.surveysmanagement.domain.ReportStats;
import eapli.base.surveysmanagement.domain.Survey;
import eapli.base.surveysmanagement.repositories.AnswerRepository;
import eapli.base.usermanagement.domain.BaseRoles;
import eapli.framework.infrastructure.authz.application.AuthorizationService;
import java.io.IOException;
import java.util.*;

/**
 * Implementation of AnswerReportController
 */
public class AnswerReportControllerImpl implements AnswerReportController{

    private final AnswerRepository answerRepository;

    private final ReportService reportService;

    private final AuthorizationService authz;

    /**
     * Initializes a new instance of AnswerReportControllerImpl
     * @param answerRepository Answer Repository
     * @param authz Authorization Service
     * @param reportService Report Service
     */
    public AnswerReportControllerImpl(AnswerRepository answerRepository, AuthorizationService authz, ReportService reportService) {
        if(answerRepository == null)
            throw new IllegalArgumentException("AnswerRepository cannot be null");
        if(authz == null)
            throw new IllegalArgumentException("AuthorizationService cannot be null");
        if (reportService == null) {
            throw new IllegalArgumentException("ReportService cannot be null");
        }
        this.answerRepository = answerRepository;
        this.reportService = reportService;
        this.authz = authz;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String generateReportOfSurvey(Survey survey) throws IOException {
        this.authz.ensureAuthenticatedUserHasAnyOf(BaseRoles.POWER_USER, BaseRoles.ADMIN, BaseRoles.SALES_CLERK);
        Iterable<Answer> answers = answerRepository.findAnswersOfGivenSurvey(survey.surveyCode());
        Iterator<Answer> answerIterator = answers.iterator();
        ReportStats reportEntry = new ReportStats();
        while(answerIterator.hasNext()){
            Answer currentAnswer = answerIterator.next();
            reportEntry = reportService.analyzeAnswerFile(currentAnswer.answersFile(), reportEntry);
        }
        return reportService.reportGenerator(survey.surveyCode().code(), reportEntry);
    }
}
