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
import eapli.base.surveysmanagement.grammar.SurveyLexer;
import eapli.base.surveysmanagement.grammar.SurveyParser;
import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * Implementation for ParseQuestionsService
 */
public class ParseQuestionsServiceImpl implements ParseQuestionsService{

    /**
     * @inheritDoc
     */
    @Override
    public Iterable<Question> parseQuestionnaireFile(byte[] questionnaireFile) throws IOException {
        InputStream questionFileStream = new ByteArrayInputStream(questionnaireFile);
        CharStream in = CharStreams.fromStream(questionFileStream);

        SurveyLexer lexer = new SurveyLexer(in);
        CommonTokenStream tokens = new CommonTokenStream(lexer);

        SurveyParser parser = new SurveyParser(tokens);
        SurveyParser.SurveyContext surveyContext = parser.survey();

        SurveyBaseVisitor baseVisitor = new SurveyBaseVisitor();
        SurveyVisitor surveyVisitor = new SurveyVisitor();
        List<Question> questionList = new ArrayList<>();

        for (SurveyParser.SectionContext sctx : surveyContext.section()) {
            for (SurveyParser.QuestionContext ctx : sctx.question()) {
                questionList.add(surveyVisitor.visitCompleteQuestion(ctx));
            }
        }
        return questionList;
    }
}
