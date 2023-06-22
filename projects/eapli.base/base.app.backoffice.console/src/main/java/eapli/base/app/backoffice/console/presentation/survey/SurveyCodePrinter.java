package eapli.base.app.backoffice.console.presentation.survey;

import eapli.base.surveysmanagement.domain.Survey;
import eapli.framework.visitor.Visitor;

public class SurveyCodePrinter implements Visitor<Survey> {

    @Override
    public void visit(Survey visitee) {
        System.out.printf("%-10s %-50s", visitee.identity(), visitee.description());
    }
}
