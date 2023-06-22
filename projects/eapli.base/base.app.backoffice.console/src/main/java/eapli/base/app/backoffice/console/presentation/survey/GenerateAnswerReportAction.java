package eapli.base.app.backoffice.console.presentation.survey;

import eapli.base.infrastructure.persistence.PersistenceContext;
import eapli.base.surveysmanagement.application.AnswerReportControllerImpl;
import eapli.base.surveysmanagement.application.ListSurveyServiceImpl;
import eapli.base.surveysmanagement.application.ReportService;
import eapli.base.surveysmanagement.application.ReportServiceImpl;
import eapli.framework.actions.Action;
import eapli.framework.infrastructure.authz.application.AuthzRegistry;

public class GenerateAnswerReportAction implements Action {

    private final GenerateAnswerReportUI generateAnswerReportUI;

    public GenerateAnswerReportAction() {
        var answerRepository = PersistenceContext.repositories().answers();

        var surveyRepository = PersistenceContext.repositories().surveys();

        var listSurveyService = new ListSurveyServiceImpl(surveyRepository);

        var reportService = new ReportServiceImpl();

        var theController = new AnswerReportControllerImpl(answerRepository, AuthzRegistry.authorizationService(), reportService);

        this.generateAnswerReportUI = new GenerateAnswerReportUI(theController,listSurveyService);

    }

    @Override
    public boolean execute() {
        return generateAnswerReportUI.show();
    }
}
