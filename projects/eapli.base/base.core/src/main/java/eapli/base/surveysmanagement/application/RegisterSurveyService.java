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

import eapli.base.surveysmanagement.domain.Survey;
import eapli.base.surveysmanagement.domain.SurveyException;
import eapli.base.surveysmanagement.domain.SurveyRule;
import java.util.Date;

/**
 * Contract for the add survey service
 */
public interface RegisterSurveyService {
    /**
     * Adds a survey
     *
     * @param surveyCode        Survey code identifier
     * @param description       Survey description
     * @param startDate         Survey being date
     * @param endDate           Survey end date
     * @param questionnaireFile Survey questionaire file
     * @param rulesToBeApplied  List of rules to be applied to the target audience of the survey
     * @return Inserted survey
     */
    Survey addSurvey(String surveyCode, String description, Date startDate, Date endDate, byte[] questionnaireFile, Iterable<SurveyRule> rulesToBeApplied) throws SurveyException;
}
