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
import eapli.framework.presentation.console.AbstractListUI;
import eapli.framework.visitor.Visitor;
import models.SurveyResponseDTO;

import java.util.ArrayList;

/**
 * Surveys implementation for UI
 */
public class ListSurveysUI extends AbstractListUI<SurveyResponseDTO> {
    private SurveysController surveysController = new SurveysControllerImpl(BaseUserApp.orderServerClient.SpomsCommunication());

    @Override
    protected Iterable<SurveyResponseDTO> elements() {
        try{
            Iterable<SurveyResponseDTO> customerSurveys = surveysController.customerSurveys(BaseUserApp.vatCode);
            return customerSurveys;

        } catch(Exception exception){
            System.out.println("A general error has occurred: " + exception.getMessage());
            return new ArrayList<>();
        }
    }

    /**
     * @inheritDoc
     */
    @Override
    protected Visitor<SurveyResponseDTO> elementPrinter() {
        return new SurveysPrinter();
    }

    /**
     * @inheritDoc
     */
    @Override
    protected String elementName() {
        return "Surveys";
    }

    /**
     * @inheritDoc
     */
    @Override
    protected String listHeader() {
        return String.format("#  %-50s%-50s%-50s%-50s%n", "CODE", "DESCRIPTION", "START DATE", "END DATE");
    }

    /**
     * @inheritDoc
     */
    @Override
    protected String emptyMessage() {
        return "No surveys ";
    }

    /**
     * @inheritDoc
     */
    @Override
    public String headline() {
        return "List available surveys for customer";
    }
}
