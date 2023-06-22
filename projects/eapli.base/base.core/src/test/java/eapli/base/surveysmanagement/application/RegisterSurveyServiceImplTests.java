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

import eapli.base.customerusermanagement.domain.*;
import eapli.base.productmanagement.domain.AlphaNumericCode;
import eapli.base.surveysmanagement.application.strategy.SurveyRuleStrategy;
import eapli.base.surveysmanagement.application.strategy.SurveyRuleStrategyFactory;
import eapli.base.surveysmanagement.domain.Survey;
import eapli.base.surveysmanagement.domain.SurveyException;
import eapli.base.surveysmanagement.domain.SurveyRule;
import eapli.base.surveysmanagement.domain.SurveyRules;
import eapli.base.surveysmanagement.repositories.SurveyRepository;
import eapli.framework.domain.repositories.IntegrityViolationException;
import eapli.framework.general.domain.model.Description;
import eapli.framework.general.domain.model.Designation;
import eapli.framework.general.domain.model.EmailAddress;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import java.util.*;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class RegisterSurveyServiceImplTests {
    @Mock
    private SurveyRepository mockSurveyRepository;

    @Mock
    private SurveyRuleStrategyFactory mockSurveyRuleStrategyFactory;

    @Mock
    private SurveyRuleStrategy mockSurveyRuleStrategy;

    private RegisterSurveyServiceImpl registerSurveyServiceImpl;

    @BeforeEach
    public void createMocks() {
        MockitoAnnotations.openMocks(this);
        registerSurveyServiceImpl = new RegisterSurveyServiceImpl(mockSurveyRepository, mockSurveyRuleStrategyFactory);
    }

    @Test
    public void addSurvey_ExistentSurveyWithSameCode_ShouldThrowIntegrityViolationException(){
        // Arrange
        Survey survey = getValidSurveyWithTargetAudience();

        var surveyRules = new ArrayList<SurveyRule>();

        Optional<Survey> optionalSurvey = Optional.of(survey);

        when(mockSurveyRepository.find(any(AlphaNumericCode.class)))
                .thenReturn(optionalSurvey);


        // Act & Assert
        assertThrows(IntegrityViolationException.class, () -> registerSurveyServiceImpl.addSurvey(survey.surveyCode().code(), survey.description().toString(),
                                                                    survey.startDate(), survey.endDate(),
                                                                    survey.questionnaireFile(), surveyRules));

        verify(mockSurveyRepository).find(any(AlphaNumericCode.class));
    }

    @Test
    public void addSurvey_NoTargetAudience_ShouldThrowSurveyException(){
        // Arrange
        Survey survey = getInvalidSurveyWithEmptyTargetAudience();

        var surveyRules = new ArrayList<SurveyRule>();

        Optional<Survey> optionalSurvey = Optional.ofNullable(null);

        when(mockSurveyRepository.find(any(AlphaNumericCode.class)))
                .thenReturn(optionalSurvey);

        // Act & Assert
        assertThrows(SurveyException.class, () -> registerSurveyServiceImpl.addSurvey(survey.surveyCode().code(), survey.description().toString(),
                                                    survey.startDate(), survey.endDate(),
                                                    survey.questionnaireFile(), surveyRules));

        verify(mockSurveyRepository).find(any(AlphaNumericCode.class));
    }

    @Test
    public void addSurvey_ValidArguments_ShouldReturnExpectedSurvey() throws SurveyException {
        // Arrange
        Survey expectedSurvey = getValidSurveyWithTargetAudience();

        Survey nullSurvey = null;

        var surveyRules = new ArrayList<SurveyRule>();

        surveyRules.add(new SurveyRule(SurveyRules.SCALAR_GENDER, "Male", ""));

        Optional<Survey> expectedResultSurvey = Optional.of(expectedSurvey);

        when(mockSurveyRepository.find(expectedSurvey.surveyCode()))
                .thenReturn(Optional.ofNullable(nullSurvey))
                        .thenReturn(expectedResultSurvey);

        when(mockSurveyRuleStrategyFactory.getStrategy(SurveyRules.SCALAR_GENDER))
                .thenReturn(mockSurveyRuleStrategy);

        when(mockSurveyRuleStrategy.performStrategy(surveyRules.get(0)))
                .thenReturn(expectedSurvey.targetAudience());

        // Act
        Survey result = registerSurveyServiceImpl.addSurvey(expectedSurvey.surveyCode().code(), expectedSurvey.description().toString(),
                                        expectedSurvey.startDate(), expectedSurvey.endDate(),
                                        expectedSurvey.questionnaireFile(), surveyRules);

        // Arrange
        assertTrue(result.sameAs(expectedSurvey));
        verify(mockSurveyRepository, times(2)).find(expectedSurvey.surveyCode());
        verify(mockSurveyRuleStrategyFactory).getStrategy(SurveyRules.SCALAR_GENDER);
        verify(mockSurveyRuleStrategy).performStrategy(surveyRules.get(0));
    }

    private Survey getInvalidSurveyWithEmptyTargetAudience(){
        Random random = new Random();

        byte[] questionaire = new byte[7];

        random.nextBytes(questionaire);

        var targetAudience = new HashSet<Customer>();

        return new Survey(AlphaNumericCode.valueOf("TEST"), Description.valueOf("TEST SURVEY WITH DESCRIPTION"),
                    Calendar.getInstance().getTime(), Calendar.getInstance().getTime(),
                    questionaire, targetAudience);
    }

    private Survey getValidSurveyWithTargetAudience(){
        Random random = new Random();

        byte[] questionaire = new byte[7];

        random.nextBytes(questionaire);

        var targetAudience = new HashSet<Customer>();
        targetAudience.addAll((Collection<? extends Customer>) getListOfCustomers());

        return new Survey(AlphaNumericCode.valueOf("TEST"), Description.valueOf("TEST SURVEY WITH DESCRIPTION"),
                Calendar.getInstance().getTime(), Calendar.getInstance().getTime(),
                questionaire, targetAudience);
    }

    private Iterable<Customer> getListOfCustomers(){
        var customers = new ArrayList<Customer>();
        var vatId = VatCode.valueOf("123456789");
        var name = Designation.valueOf("TEST CUSTOMER");
        var emailAddress = EmailAddress.valueOf("test@hotmail.com");
        var phoneNumber = PhoneNumber.valueOf(912223432);
        var gender = Gender.valueOf("Male");
        var birthDate = BirthDate.valueOf(Calendar.getInstance().getTime());
        var billingAddress = Address.valueOf("STREET", "23", "CITY");
        var deliveryAddress = Address.valueOf("STREET", "23", "CITY");

        customers.add(new Customer(vatId, name, emailAddress, phoneNumber, gender, birthDate, billingAddress, deliveryAddress));

        return customers;
    }
}
