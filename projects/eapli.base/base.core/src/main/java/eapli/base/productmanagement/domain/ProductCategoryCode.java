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

package eapli.base.productmanagement.domain;

import eapli.framework.domain.model.ValueObject;
import eapli.framework.strings.util.StringPredicates;

import javax.persistence.Embeddable;

@Embeddable
public class ProductCategoryCode implements ValueObject, Comparable<ProductCategoryCode>  {
    private String code;

    protected ProductCategoryCode(final String alphaNumericCode) {
        if (StringPredicates.isNullOrEmpty(alphaNumericCode)) {
            throw new IllegalArgumentException(
                    "Product Category Code should neither be null nor empty");
        }

        if(alphaNumericCode.length() > 10)
            throw new IllegalArgumentException("Product Category Code cannot have more than 10 characters");

        this.code = alphaNumericCode;
    }

    protected ProductCategoryCode() {
        // for ORM
        this.code = null;
    }

    public String code(){
        return this.code;
    }

    public static ProductCategoryCode valueOf(final String alphaNumericCode) {
        return new ProductCategoryCode(alphaNumericCode);
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ProductCategoryCode)) {
            return false;
        }

        final ProductCategoryCode that = (ProductCategoryCode) o;
        return this.code.equals(that.code);
    }

    @Override
    public int hashCode() {
        return this.code.hashCode();
    }

    @Override
    public String toString() {
        return this.code;
    }

    @Override
    public int compareTo(ProductCategoryCode o) {
        return code.compareTo(o.code);
    }
}
