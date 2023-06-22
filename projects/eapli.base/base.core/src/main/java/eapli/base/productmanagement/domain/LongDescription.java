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
public class LongDescription implements ValueObject, Comparable<LongDescription>  {
    private String value;

    protected LongDescription(final String description) {
        if (StringPredicates.isNullOrEmpty(description)) {
            throw new IllegalArgumentException(
                    "Long Description should neither be null nor empty");
        }

        var descriptionLength = description.length();

        if(descriptionLength < 20 || descriptionLength > 50)
            throw new IllegalArgumentException("Long Description must have a minimum of 20 characters and maximum of 50.");

        this.value = description;
    }

    protected LongDescription() {
        // for ORM
        this.value = null;
    }

    public String code(){
        return this.value;
    }

    public static LongDescription valueOf(final String description) {
        return new LongDescription(description);
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof LongDescription)) {
            return false;
        }

        final LongDescription that = (LongDescription) o;
        return this.value.equals(that.value);
    }

    @Override
    public int hashCode() {
        return this.value.hashCode();
    }

    @Override
    public String toString() {
        return this.value;
    }

    @Override
    public int compareTo(LongDescription o) {
        return value.compareTo(o.value);
    }
}
