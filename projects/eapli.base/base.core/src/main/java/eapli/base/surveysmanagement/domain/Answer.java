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

import eapli.base.customerusermanagement.domain.VatCode;
import eapli.base.productmanagement.domain.AlphaNumericCode;
import eapli.framework.domain.model.AggregateRoot;
import javax.persistence.*;

/**
 * Entity for handling the answers of surveys
 */
@Entity
public class Answer implements AggregateRoot<Long> {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long answerGenId;

    @AttributeOverride(name = "code", column = @Column(name = "surveyCode"))
    private AlphaNumericCode surveyCode;

    @AttributeOverride(name = "code", column = @Column(name = "customerID"))
    private VatCode customerID;

    @Lob
    private byte[] answersFile;

    /**
     * Initializes a new instance of Answer
     * @param surveyCode SurveyCode
     * @param customerID CustomerId
     * @param answersFile Answers File
     */
    public Answer(AlphaNumericCode surveyCode, VatCode customerID, byte[] answersFile) {
        this.surveyCode = surveyCode;
        this.customerID = customerID;
        this.answersFile = answersFile;
    }

    /**
     * Initializes a new instance of Answer(for ORM use only)
     */
    protected Answer(){
        //for ORM
    }

    /**
     * @inheritDoc
     *
     */
    @Override
    public boolean sameAs(Object other) {
        if(!(other instanceof Answer)) return false;
        final Answer that = (Answer) other;
        if(this == that) return true;

        return identity().equals(that.identity()) && surveyCode.equals(that.surveyCode)
                && customerID.equals(that.customerID) && answersFile.equals(that.answersFile);
    }

    /**
     * @inheritDoc
     *
     */
    @Override
    public int compareTo(Long other) {
        return AggregateRoot.super.compareTo(other);
    }

    /**
     * @inheritDoc
     *
     */
    @Override
    public Long identity() {
        return answerGenId;
    }

    /**
     * @inheritDoc
     *
     */
    @Override
    public boolean hasIdentity(Long id) {
        return AggregateRoot.super.hasIdentity(id);
    }

    /**
     * Survey code
     * @return AlphaNumericCode
     */
    public AlphaNumericCode surveyCode(){ return surveyCode;}

    /**
     * Customer ID
     * @return VatCode
     */
    public VatCode customerID() { return customerID;}

    /**
     * Answers file
     * @return byte array
     */
    public byte[] answersFile(){ return answersFile;}
}
