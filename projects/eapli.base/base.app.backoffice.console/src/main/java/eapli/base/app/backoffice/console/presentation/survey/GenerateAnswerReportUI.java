package eapli.base.app.backoffice.console.presentation.survey;

import com.sun.istack.NotNull;
import eapli.base.surveysmanagement.application.AnswerReportController;
import eapli.base.surveysmanagement.application.ListSurveyService;
import eapli.base.surveysmanagement.domain.Survey;
import eapli.framework.io.util.Console;
import eapli.framework.presentation.console.AbstractUI;
import eapli.framework.presentation.console.SelectWidget;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class GenerateAnswerReportUI extends AbstractUI {

    private final AnswerReportController theController;

    private final ListSurveyService listSurveyService;

    public GenerateAnswerReportUI(@NotNull AnswerReportController theController, ListSurveyService listSurveyService) {
        this.theController = theController;
        this.listSurveyService = listSurveyService;
    }

    @Override
    protected boolean doShow() {
        final Iterable<Survey> surveys = listSurveyService.surveys();
        System.out.println("Choose a Survey to generate a report from:");
        final SelectWidget<Survey> selector = new SelectWidget<>("Surveys:", surveys, new SurveyCodePrinter());
        selector.show();
        final Survey theSurvey = selector.selectedElement();
        if(selector.selectedOption()==0){
            return false;
        }
        try {
            String report = theController.generateReportOfSurvey(theSurvey);
            final String answerReportPath = Console.readLine("Where do you want to save the report?");
            Path path = Paths.get(answerReportPath+"Report_"+theSurvey.surveyCode().code()+".txt");
            try{
                Files.writeString(path,report, StandardCharsets.UTF_8);
            }catch(IOException ex){
                System.out.println("The system can not save this report. The path is not valid.");
            }
            System.out.println(report);
        } catch (IOException e) {
            System.out.println("The answer file is not found in the database, an error has occurred.");
        }
        return false;
    }

    @Override
    public String headline() {
        return "Generate Answers Report.";
    }
}
