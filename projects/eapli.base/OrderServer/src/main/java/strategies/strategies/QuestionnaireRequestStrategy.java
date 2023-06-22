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

package strategies.strategies;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import eapli.base.infrastructure.persistence.PersistenceContext;
import eapli.base.productmanagement.domain.AlphaNumericCode;
import eapli.base.surveysmanagement.domain.Survey;
import eapli.base.surveysmanagement.repositories.SurveyRepository;
import enums.MessageCode;
import enums.ProtocolVersion;
import models.*;

/**
 * Strategy implementation for questionnaire request
 */
public class QuestionnaireRequestStrategy implements SpomsStrategy{
    private final SurveyRepository surveyRepository;

    /**
     * Initializes a new instance of QuestionnaireRequestStrategy
     * @param surveyRepository SurveyRepository
     */
    public QuestionnaireRequestStrategy(SurveyRepository surveyRepository){
        if(surveyRepository == null)
            throw new IllegalArgumentException("SurveyRepository cannot be null");

        this.surveyRepository = surveyRepository;
    }

    /**
     * @inheritDoc
     */
    @Override
    public SpomsPacket PerformStrategy(SpomsPacket packetToHandle) throws JsonProcessingException {
        var packetData = "";
        var objectMapper = new ObjectMapper();

        try {

             var questionnaireRequestDTO = objectMapper.readValue(packetToHandle.data(), QuestionnaireRequestDTO.class);

             var survey = surveyRepository.find(AlphaNumericCode.valueOf(questionnaireRequestDTO.surveyCode));

             if(survey.isPresent()){
                 var questionnaireDTO = mapFromDomainEntityToDTO(survey.get());

                 packetData = objectMapper.writeValueAsString(questionnaireDTO);
             }else{
                 packetData = objectMapper.writeValueAsString(new QuestionnaireDTO());
             }

        }catch (Exception ex)
        {
            packetData = objectMapper.writeValueAsString(new QuestionnaireDTO());
        }

        return new SpomsPacketBuilder()
                .messageCode(MessageCode.QUESTIONNAIRE_RESPONSE)
                .protocolVersion(ProtocolVersion.VERSION_1)
                .data(packetData)
                .build();
    }

    private QuestionnaireDTO mapFromDomainEntityToDTO(Survey domainSurvey){
        var dtoQuestionnaire = new QuestionnaireDTO();

        dtoQuestionnaire.surveyCode = domainSurvey.surveyCode().code();
        dtoQuestionnaire.questionnaire = domainSurvey.questionnaireFile();

        return dtoQuestionnaire;
    }
}
