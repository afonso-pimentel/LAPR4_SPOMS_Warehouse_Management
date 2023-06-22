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

import eapli.base.surveysmanagement.domain.ReportStats;
import java.io.IOException;

/**
 * Service for generating report stats for the answers of surveys
 */
public interface ReportService {

    /**
     * This method analyzes an answer file, visiting
     * single-choice, multiple-choice and sorting choice type of answers.
     * It resorts to the visitor methods with custom return actions to obtain the values
     * we need to calculate the percentages.
     *
     * It also fills a ReportEntry object that holds Maps with the references and values
     * per Question/Answer. This ReportEntry is continuously filled, it is returned and
     * entered as an argument.
     *
     * @param answerFile
     * @param reportEntry
     * @return
     * @throws IOException
     */
    ReportStats analyzeAnswerFile(byte[] answerFile, ReportStats reportEntry) throws IOException;

    /**
     * This method returns a string that is being built with the structure
     * defined per type of answer and wanted fields.
     *
     * @param surveyID
     * @param entry
     * @return
     */
    String reportGenerator(String surveyID, ReportStats entry);
}
