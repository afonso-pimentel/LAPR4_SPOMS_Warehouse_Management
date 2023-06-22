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

package eapli.base.surveysmanagement.domain;

import java.util.List;

/**
 * Question model
 */
public class Question {

    private final Integer questionID;

    private final String obligatoriness;

    private final String questionText;

    private final List<String> options;

    private final String inputQuestion;

    private final String condition;


    /**
     * Initializes a new instance of Question
     * @param questionID QuestionID
     * @param obligatoriness Obligatoriness
     * @param questionText Question text
     * @param options Options
     * @param inputQuestion Input Question
     * @param condition Condition
     */
    public Question(Integer questionID, String obligatoriness, String questionText, List<String> options, String inputQuestion, String condition) {
        this.questionID = questionID;
        this.obligatoriness = obligatoriness;
        this.questionText = questionText;
        this.options = options;
        this.inputQuestion = inputQuestion;
        this.condition = condition;
    }

    /**
     * Obligatoriness of que estion
     * @return String
     */
    public String obligatoriness() {
        return obligatoriness;
    }

    /**
     * Question ID
     * @return Integer
     */
    public Integer questionID() {
        return questionID;
    }

    /**
     * Question text
     * @return String
     */
    public String questionText() {
        return questionText;
    }

    /**
     * Options
     * @return List of options
     */
    public List<String> options() {
        return options;
    }

    /**
     * Input question
     * @return String
     */
    public String inputQuestion() {
        return inputQuestion;
    }

    /**
     * Condition
     * @return
     */
    public String condition() {
        return condition;
    }

    /**
     * @inheritDoc
     *
     */
    @Override
    public String toString() {
        StringBuilder questionBuilder = new StringBuilder();
        questionBuilder.append(questionID+"-"+questionText+"\n");
        return questionBuilder.toString();
    }
}
