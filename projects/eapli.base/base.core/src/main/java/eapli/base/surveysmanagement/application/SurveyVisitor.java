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

import eapli.base.surveysmanagement.domain.Question;
import eapli.base.surveysmanagement.grammar.SurveyBaseVisitor;
import eapli.base.surveysmanagement.grammar.SurveyParser;
import org.antlr.v4.runtime.tree.TerminalNode;
import java.util.ArrayList;
import java.util.List;

/**
 * Visitor class to handle Surveys
 */
public class SurveyVisitor extends SurveyBaseVisitor<String> {

    /**
     * This method has to return a String with everything that is in text for a question
     *
     * @param ctx
     * @return
     */
    public Question visitCompleteQuestion(SurveyParser.QuestionContext ctx) {
        Integer questionID = Integer.parseInt(ctx.question_ID().getText());

        String questionText = ctx.QUESTION_TEXT().getText();
        String obligatoriness = ctx.OBLIGATORINESS().getText();

        List<String> options = null;

        String inputQuestion = null;
        String condition = null;
        String scaleValueHeader = null;

        try {
            scaleValueHeader = ctx.SCALE_VALUE_HEADER().getText();
        } catch (Exception e1) {}

        try{
            options = visitOptions(ctx);
        } catch(Exception e2) {}

        try{
            inputQuestion =  ctx.why().getText();
        }catch(Exception e3){}

        try{
            condition = ctx.CONDITION().getText();
        }catch (Exception e4){}

        return new Question(questionID, obligatoriness, questionText, options, inputQuestion, condition);
    }

    /**
     * Visits all the options within a Survey
     * @param ctx Question context
     * @return List of options
     */
    public List<String> visitOptions(SurveyParser.QuestionContext ctx) {
        List<String> optionsList = new ArrayList<>();
        try {
            List<TerminalNode> options = ctx.OPTION();
            for (TerminalNode option : options) {
                optionsList.add(option.getText());
            }
        }catch(Exception e){

        }
        return optionsList;
    }
}
