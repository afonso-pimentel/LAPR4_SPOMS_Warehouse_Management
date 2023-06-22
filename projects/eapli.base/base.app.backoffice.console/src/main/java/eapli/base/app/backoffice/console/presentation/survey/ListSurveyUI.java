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

package eapli.base.app.backoffice.console.presentation.survey;

import eapli.base.surveysmanagement.application.ListSurveyController;
import eapli.base.surveysmanagement.application.ListSurveyControllerImpl;
import eapli.base.surveysmanagement.domain.Survey;
import eapli.framework.presentation.console.AbstractUI;

/**
 * Extends AbstractUI
 */
public class ListSurveyUI extends AbstractUI {
    private final ListSurveyController listSurveyController;

    /**
     * Initializes a new instance of RegisterSurveyUI
     */
    public ListSurveyUI(){
        this.listSurveyController = new ListSurveyControllerImpl();
    }

    /**
     * @inheritDoc
     *
     */
    @Override
    protected boolean doShow() {
        try{
            Iterable<Survey> surveys = listSurveyController.surveys();

            var surveyPrinter = new SurveyPrinter();

            for(Survey survey : surveys){
                surveyPrinter.visit(survey);
            }

        } catch(Exception e){
            System.out.println("A general error has occurred: " + e.getMessage());
        }
        return false;
    }

    /**
     * @inheritDoc
     *
     */
    @Override
    public String headline() {
        return "List surveys";
    }
}
