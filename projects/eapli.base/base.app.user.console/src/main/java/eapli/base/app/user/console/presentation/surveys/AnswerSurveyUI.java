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

import eapli.base.app.user.console.BaseUserApp;
import eapli.base.customerusermanagement.domain.VatCode;
import eapli.base.productmanagement.domain.AlphaNumericCode;
import eapli.base.surveysmanagement.domain.Question;
import eapli.base.surveysmanagement.domain.SurveyRules;
import eapli.framework.io.util.Console;
import eapli.framework.presentation.console.AbstractUI;
import eapli.framework.presentation.console.SelectWidget;
import models.QuestionnaireDTO;
import models.SurveyResponseDTO;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Answer survey UI
 */
public class AnswerSurveyUI extends AbstractUI {
    private SurveysController surveysController = new SurveysControllerImpl(BaseUserApp.orderServerClient.SpomsCommunication());

    /**
     * @inheritDoc
     */
    @Override
    protected boolean doShow() {
        try {
            final Iterable<SurveyResponseDTO> listOfSurveysDTOS = surveysController.customerSurveys(BaseUserApp.vatCode);

            if(!listOfSurveysDTOS.iterator().hasNext()){
                System.out.println("No surveys found for answering");
                return false;
            }

            final var selectedSurvey = new SelectWidget<SurveyResponseDTO>("Available surveys: ", listOfSurveysDTOS, new SurveysPrinter());

            selectedSurvey.show();

            if (selectedSurvey.selectedOption() != 0) {
                final QuestionnaireDTO surveyQuestionnaire = surveysController.getQuestionnaire(selectedSurvey.selectedElement().surveyCode);

                if (surveyQuestionnaire.surveyCode != null && surveyQuestionnaire.surveyCode != "") {

                    Iterable<Question> questionIterable = surveysController.parseQuestions(surveyQuestionnaire.questionnaire);
                    String answer = answerQuestionnaire(questionIterable);
                    surveysController.registerAnswer(AlphaNumericCode.valueOf(surveyQuestionnaire.surveyCode), VatCode.valueOf(String.valueOf(BaseUserApp.vatCode)), answer);

                    System.out.println("Survey answer was successfully saved!");

                } else {
                    System.out.println("Questionnaire not found!");
                }
            }

        } catch (Exception exception) {
            System.out.println("A general error has occurred: " + exception.getMessage());
        }
        return false;
    }

    /**
     * @inheritDoc
     */
    @Override
    public String headline() {
        return "Answer survey";
    }

    private String answerQuestionnaire(Iterable<Question> questionIterable) {
        String answer = "";
        String fileString = "";
        Iterator<Question> iterator = questionIterable.iterator();
        while (iterator.hasNext()) {
            Question currentQuestion = iterator.next();
            System.out.println(currentQuestion.toString());
            String prompt = "Answer:";
            List<String> options = new ArrayList<>();
            List<String> answers = new ArrayList<>();
            String flag = "1";

            if (!currentQuestion.options().isEmpty()) {
                options = currentQuestion.options();
                for (String option : options)
                    System.out.println(option);
                do {
                    answer = Console.readLine(prompt);
                    answer = answer + "(" + options.get(Integer.parseInt(answer) - 1) + ")";
                    flag = Console.readLine("To add more options, press +. To end it, press 0.");
                    if (flag.equals("+"))
                        answers.add(answer);
                    if (flag.equals("0")) {
                        if (!answers.isEmpty()) {
                            answers.add(answer);
                        }
                    }
                } while (!flag.equals("0"));

                if (!answers.isEmpty()) {
                    flag = Console.readLine("If it was a sorting answer, enter 1.");
                    int num = answers.size();
                    if (flag.equals("1")) {
                        answer = "#";
                        for (int i = 0; i < num; i++) {
                            if (i == num - 1) {
                                answer += answers.get(i);
                            } else {
                                answer += answers.get(i) + ",\n";
                            }
                        }
                        answer += "#";
                    } else {
                        answer = "{";
                        for (int i = 0; i < num; i++) {
                            if (i == num - 1) {
                                answer += answers.get(i);
                            } else {
                                answer += answers.get(i) + ",\n";
                            }
                        }
                        answer += "}";
                        }
                    }
                } else {
                    answer = Console.readLine(prompt);
                }
                if (currentQuestion.inputQuestion() != null) {
                    prompt = currentQuestion.inputQuestion();
                    String inputAnswer = Console.readLine(prompt);
                    answer += "\n%in%" + inputAnswer + "%in%";
                }
                answer = currentQuestion.questionID() + "::\n" + answer+";\n";
                fileString += answer;
        }
        return fileString;
    }
}
