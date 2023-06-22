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
import eapli.base.customerusermanagement.domain.VatCode;
import eapli.base.infrastructure.persistence.PersistenceContext;
import eapli.base.surveysmanagement.domain.Answer;
import eapli.base.surveysmanagement.domain.Survey;
import eapli.base.surveysmanagement.repositories.AnswerRepository;
import eapli.base.surveysmanagement.repositories.SurveyRepository;
import enums.MessageCode;
import enums.ProtocolVersion;
import models.SpomsPacket;
import models.SpomsPacketBuilder;
import models.SurveyResponseDTO;
import models.SurveysRequestDTO;
import java.util.ArrayList;
import java.util.Optional;
import java.util.stream.StreamSupport;

/**
 * Strategy implementation for the request to obtain surveys
 */
public class SurveysRequestStrategy implements SpomsStrategy {
    private final SurveyRepository surveyRepository;
    private final AnswerRepository answerRepository;

    /**
     * Initializes a new instance of SurveysRequestStrategy
     * @param surveyRepository SurveyRepository
     */
    public SurveysRequestStrategy(SurveyRepository surveyRepository, AnswerRepository answerRepository){
        if(surveyRepository == null)
            throw new IllegalArgumentException("SurveyRepository cannot be null");

        if(answerRepository == null)
            throw new IllegalArgumentException("AnswerRepository cannot be null");

        this.surveyRepository = surveyRepository;
        this.answerRepository = answerRepository;
    }

    /**
     * @inheritDoc
     */
    @Override
    public SpomsPacket PerformStrategy(SpomsPacket packetToHandle) throws JsonProcessingException {
        var packetData = "";
        var objectMapper = new ObjectMapper();

        try {
            var surveysRequest = objectMapper.readValue(packetToHandle.data(), SurveysRequestDTO.class);

            var activeSurveysForCustomer = surveyRepository.findActiveSurveysForCustomer(VatCode.valueOf(String.valueOf(surveysRequest.customerVatCode)));

            Iterable<Survey> surveysToAnswer = findSurveysNonAnswered(activeSurveysForCustomer, surveysRequest.customerVatCode);

            Iterable<SurveyResponseDTO> surveyResponseDTOS = mapFromDomainEntitiesToDTOs(surveysToAnswer);

            packetData = objectMapper.writeValueAsString(surveyResponseDTOS);

        }catch (Exception ex)
        {
            packetData = objectMapper.writeValueAsString(new ArrayList<SurveyResponseDTO>());
        }

        return new SpomsPacketBuilder()
                .messageCode(MessageCode.CUSTOMER_SURVEYS_RESPONSE)
                .protocolVersion(ProtocolVersion.VERSION_1)
                .data(packetData)
                .build();
    }

    private Iterable<SurveyResponseDTO> mapFromDomainEntitiesToDTOs(Iterable<Survey> domainSurveys){
        var dtoSurveys = new ArrayList<SurveyResponseDTO>();

        for(Survey survey: domainSurveys){
            var dtoSurvey = new SurveyResponseDTO();
            dtoSurvey.surveyCode = survey.surveyCode().code();
            dtoSurvey.description = survey.description().toString();
            dtoSurvey.startDate = survey.startDate();
            dtoSurvey.endDate = survey.endDate();

            dtoSurveys.add(dtoSurvey);
        }

        return dtoSurveys;
    }

    private Iterable<Survey> findSurveysNonAnswered(Iterable<Survey> inputSurveyList, long customerVatCode){
        var nonAnsweredSurveys = new ArrayList<Survey>();

        for(Survey survey : inputSurveyList){
            Iterable<Answer> answersToSurvey = answerRepository.findAnswersOfGivenSurvey(survey.surveyCode());

            Optional<Answer> surveyAnswer = StreamSupport.stream(answersToSurvey.spliterator(), false)
                    .filter(o -> o.customerID().equals(VatCode.valueOf(String.valueOf(customerVatCode))))
                    .findFirst();

            if(!surveyAnswer.isPresent())
                nonAnsweredSurveys.add(survey);
        }

        return nonAnsweredSurveys;
    }
}
