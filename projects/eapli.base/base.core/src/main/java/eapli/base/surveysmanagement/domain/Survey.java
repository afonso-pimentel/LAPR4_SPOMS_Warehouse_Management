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

import eapli.base.customerusermanagement.domain.Customer;
import eapli.base.productmanagement.domain.AlphaNumericCode;
import eapli.framework.domain.model.AggregateRoot;
import eapli.framework.general.domain.model.Description;
import javax.persistence.*;
import java.util.Date;
import java.util.Set;

/**
 * Entity for handling surveys
 */
@Entity
public class Survey implements AggregateRoot<Long> {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long surveyGenId;

    @AttributeOverride(name = "code", column = @Column(name = "surveyCode"))
    private AlphaNumericCode surveyCode;

    @AttributeOverride(name = "value", column = @Column(name = "surveyDescription"))
    private Description description;

    @Temporal(TemporalType.TIMESTAMP)
    private Date startDate;

    @Temporal(TemporalType.TIMESTAMP)
    private Date endDate;

    @Lob
    private byte[] questionnaireFile;

    @ManyToMany
    private Set<Customer> targetAudience;

    /**
     * Initializes a new instance of Survey
     * @param surveyCode SurveyCode
     * @param description Description
     * @param startDate Start Date
     * @param endDate End Date
     * @param questionnaireFile Questionaire File
     * @param targetAudience Target Audience
     */
    public Survey(AlphaNumericCode surveyCode, Description description, Date startDate, Date endDate, byte[] questionnaireFile, Set<Customer> targetAudience) {
        this.surveyCode = surveyCode;
        this.description = description;
        this.startDate = startDate;
        this.endDate = endDate;
        this.questionnaireFile = questionnaireFile;
        this.targetAudience = targetAudience;
    }

    /**
     * Initializes a new instance of Survey
     */
    protected Survey(){
        //for ORM
    }

    /**
     * Survey code
     * @return AlphaNumericCode
     */
    public AlphaNumericCode surveyCode(){ return surveyCode;}

    /**
     * Description
     * @return Description
     */
    public Description description() {return description;}

    /**
     * Questionnaire file
     * @return byte array
     */
    public byte[] questionnaireFile() {return questionnaireFile;}

    /**
     * Start date
     * @return Date
     */
    public Date startDate(){ return startDate;}

    /**
     * End date
     * @return Date
     */
    public Date endDate(){ return endDate;}

    /**
     * Target audience
     * @return Set of customers
     */
    public Set<Customer> targetAudience(){return targetAudience;}

    /**
     * @inheritDoc
     *
     */
    @Override
    public boolean sameAs(Object other) {
        if(!(other instanceof Survey)) return false;
        final Survey that = (Survey) other;
        if(this == that) return true;

        return identity().equals(that.identity()) && surveyCode.equals(that.surveyCode)
               && description.equals(that.description) && questionnaireFile.equals(that.questionnaireFile)
               && startDate.equals(that.startDate) && endDate.equals(this.endDate) && targetAudience.equals(that.targetAudience);
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
        return this.surveyGenId;
    }

    /**
     * @inheritDoc
     *
     */
    @Override
    public boolean hasIdentity(Long surveyGenId) {
        return AggregateRoot.super.hasIdentity(surveyGenId);
    }
}
