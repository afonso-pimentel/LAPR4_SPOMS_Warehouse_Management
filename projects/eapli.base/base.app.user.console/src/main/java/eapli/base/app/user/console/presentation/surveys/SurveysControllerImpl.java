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

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.type.CollectionType;
import com.fasterxml.jackson.databind.type.TypeFactory;
import communication.SpomsCommunication;
import eapli.base.customerusermanagement.domain.VatCode;
import eapli.base.productmanagement.domain.AlphaNumericCode;
import eapli.base.surveysmanagement.application.ParseQuestionsServiceImpl;
import eapli.base.surveysmanagement.domain.Answer;
import eapli.base.surveysmanagement.domain.Question;
import enums.MessageCode;
import enums.ProtocolVersion;
import models.*;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

/**
 * Implementation of SurveysController
 */
public class SurveysControllerImpl implements SurveysController{
    private final SpomsCommunication spomsCommunication;
    private ParseQuestionsServiceImpl parseQuestionService;

    /**
     * Initializes a new instance of SurveysControllerImpl
     * @param spomsCommunication SpomsCommunication
     */
    public SurveysControllerImpl(SpomsCommunication spomsCommunication){
        if(spomsCommunication == null)
            throw new IllegalArgumentException("SpomsCommunication cannot be null");

        this.spomsCommunication = spomsCommunication;
        this.parseQuestionService = new ParseQuestionsServiceImpl();
    }

    /**
     * @inheritDoc
     */
    @Override
    public Iterable<Question> parseQuestions(byte[] questionnaireFile) throws IOException {
        return parseQuestionService.parseQuestionnaireFile(questionnaireFile);
    }

    /**
     * @inheritDoc
     */
    @Override
    public Iterable<SurveyResponseDTO> customerSurveys(long vatCode) {
        try{
            var objectMapper = new ObjectMapper();

            var customerSurveysRequest = new SurveysRequestDTO();
            customerSurveysRequest.customerVatCode = vatCode;

            var requestData = objectMapper.writeValueAsString(customerSurveysRequest);

            var request = new SpomsPacketBuilder()
                    .messageCode(MessageCode.CUSTOMER_SURVEYS_REQUEST)
                    .protocolVersion(ProtocolVersion.VERSION_1)
                    .data(requestData)
                    .build();

            var response = spomsCommunication.getResponse(request, MessageCode.CUSTOMER_SURVEYS_RESPONSE);

            CollectionType typeReference =
                    TypeFactory.defaultInstance().constructCollectionType(List.class, SurveyResponseDTO.class);

            return objectMapper.readValue(response.data(), typeReference);

        } catch(Exception exception){
            return new ArrayList<>();
        }
    }

    /**
     * @inheritDoc
     */
    @Override
    public QuestionnaireDTO getQuestionnaire(String surveyCode) {
        try{
            var objectMapper = new ObjectMapper();

            var questionnaireRequest = new QuestionnaireRequestDTO();
            questionnaireRequest.surveyCode = surveyCode;

            var requestData = objectMapper.writeValueAsString(questionnaireRequest);

            var request = new SpomsPacketBuilder()
                    .messageCode(MessageCode.QUESTIONNAIRE_REQUEST)
                    .protocolVersion(ProtocolVersion.VERSION_1)
                    .data(requestData)
                    .build();

            var response = spomsCommunication.getResponse(request, MessageCode.QUESTIONNAIRE_RESPONSE);

            return objectMapper.readValue(response.data(), QuestionnaireDTO.class);

        } catch(Exception exception){
            return new QuestionnaireDTO();
        }
    }

    /**
     *
     * @inheritDoc
     */
    @Override
    public Answer registerAnswer(AlphaNumericCode surveyCode, VatCode customerID, String questionnaireAnswer) {
       try{
           var answerFile = questionnaireAnswer.getBytes(StandardCharsets.UTF_8);
           var answerRequest = new AnswerDTO();

           answerRequest.answersFile = answerFile;
           answerRequest.customerID = customerID.toString();
           answerRequest.surveyCode = surveyCode.code();

           var objectMapper = new ObjectMapper();
           var requestData = objectMapper.writeValueAsString(answerRequest);

           var request = new SpomsPacketBuilder()
                   .messageCode(MessageCode.ANSWER_REQUEST)
                   .protocolVersion(ProtocolVersion.VERSION_1)
                   .data(requestData)
                   .build();

            var response = spomsCommunication.getResponse(request, MessageCode.ANSWER_RESPONSE);
            var answerResponse = objectMapper.readValue(response.data(), AnswerDTO.class);

            System.out.println("Survey answer was saved successfully");

            return new Answer(AlphaNumericCode.valueOf(answerResponse.surveyCode), VatCode.valueOf(answerResponse.customerID), answerResponse.answersFile);

       } catch(Exception exception){
           var answerFile = questionnaireAnswer.getBytes(StandardCharsets.UTF_8);
           return new Answer(surveyCode,customerID,answerFile);
       }

    }

}
