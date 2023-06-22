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

package eapli.base.app.user.console.presentation.surveys;

import eapli.base.customerusermanagement.domain.VatCode;
import eapli.base.productmanagement.domain.AlphaNumericCode;
import eapli.base.surveysmanagement.domain.Answer;
import eapli.base.surveysmanagement.domain.Question;
import models.QuestionnaireDTO;
import models.SurveyResponseDTO;

import java.io.IOException;

/**
 * Contract for Surveys controller
 */
public interface SurveysController {

    /**
     * Method to return an Iterable with all of the questions in the format we need
     * from the questionnaireFile that was persisted in the db from a given survey.
     * @param questionnaireFile
     * @return
     * @throws IOException
     */
    Iterable<Question> parseQuestions(byte[] questionnaireFile) throws IOException;

    /**
     * Obtain's a list of the current surveys that need to be answered
     * @param vatCode Customer vatCode
     * @return List of Survey's
     */
    Iterable<SurveyResponseDTO> customerSurveys(long vatCode);

    /**
     * Returns the questionnaire for the specified survey code
     * @param surveyCode Survey Code
     * @return Questionnaire
     */
    QuestionnaireDTO getQuestionnaire(String surveyCode);

    /**
     * Method to make the request to order server and save the answer in the database.
     * @param surveyCode
     * @param customerID
     * @param questionnaireAnswer
     * @return
     */
    Answer registerAnswer(AlphaNumericCode surveyCode, VatCode customerID, String questionnaireAnswer);
}
