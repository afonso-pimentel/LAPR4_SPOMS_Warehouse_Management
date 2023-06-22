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

package eapli.base.persistence.impl.jpa;

import eapli.base.Application;
import eapli.base.customerusermanagement.domain.Customer;
import eapli.base.customerusermanagement.domain.VatCode;
import eapli.base.ordersmanagement.domain.Order_;
import eapli.base.productmanagement.domain.AlphaNumericCode;
import eapli.base.surveysmanagement.domain.Survey;
import eapli.base.surveysmanagement.repositories.SurveyRepository;
import eapli.framework.domain.repositories.TransactionalContext;
import eapli.framework.infrastructure.repositories.impl.jpa.JpaAutoTxRepository;

import javax.persistence.TypedQuery;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

/**
 * Implementation of SurveyRepository
 */
public class JpaSurveyRepository
        extends JpaAutoTxRepository<Survey, Long, Long>
        implements SurveyRepository {

    /**
     * Initializes a new instance of JpaSurveyRepository
     * @param autoTx
     */
    public JpaSurveyRepository(final TransactionalContext autoTx) { super(autoTx,"surveyGenId");}

    /**
     * Initializes a new instance of JpaSurveyRepository
     * @param puname
     */
    public JpaSurveyRepository(final String puname){
        super(puname, Application.settings().getExtendedPersistenceProperties(),
                "surveyGenId");
    }

    /**
     * @inheritDoc
     *
     */
    @Override
    public Optional<Survey> find(AlphaNumericCode code) {
        var query = new StringBuilder();
        query.append("e.surveyCode = :surveyCode");

        var params = new HashMap<String, Object>();
        params.put("surveyCode", code);

        return matchOne(query.toString(), params);
    }

    /**
     * @inheritDoc
     *
     */
    @Override
    public Iterable<Survey> findActiveSurveysForCustomer(VatCode customerVatCode) {
        var customQuery = new StringBuilder();

        customQuery.append("SELECT e ");
        customQuery.append("FROM Survey e ");
        customQuery.append("LEFT JOIN FETCH e.targetAudience p where p.vatID = :customerVatCode AND GETDATE() BETWEEN e.startDate AND e.endDate");

        final TypedQuery<Survey> query = entityManager().createQuery(customQuery.toString(), Survey.class)
                .setParameter("customerVatCode", customerVatCode);

        return query.getResultList();
    }
}
