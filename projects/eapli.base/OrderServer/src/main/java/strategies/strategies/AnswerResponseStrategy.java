package strategies.strategies;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import eapli.base.customerusermanagement.domain.VatCode;
import eapli.base.productmanagement.domain.AlphaNumericCode;
import eapli.base.surveysmanagement.domain.Answer;
import eapli.base.surveysmanagement.repositories.AnswerRepository;
import enums.MessageCode;
import enums.ProtocolVersion;
import models.AnswerDTO;
import models.SpomsPacket;
import models.SpomsPacketBuilder;

public class AnswerResponseStrategy implements SpomsStrategy{

    private final AnswerRepository answerRepository;

    public AnswerResponseStrategy(AnswerRepository answerRepository) {
        if(answerRepository == null)
            throw new IllegalArgumentException("AnswerRepository cannot be null");
        this.answerRepository = answerRepository;
    }


    @Override
    public SpomsPacket PerformStrategy(SpomsPacket packetToHandle) throws JsonProcessingException {
        var packetData = "";
        var objectMapper = new ObjectMapper();
        try{
            var answerDTO = objectMapper.readValue(packetToHandle.data(), AnswerDTO.class);
            var answer = new Answer(AlphaNumericCode.valueOf(answerDTO.surveyCode), VatCode.valueOf(answerDTO.customerID), answerDTO.answersFile);
            answerRepository.save(answer);
        }catch (Exception ex)
        {
            String string = ex.getMessage();
        }

        return new SpomsPacketBuilder()
                .messageCode(MessageCode.ANSWER_RESPONSE)
                .protocolVersion(ProtocolVersion.VERSION_1)
                .data(packetData)
                .build();
    }
}
