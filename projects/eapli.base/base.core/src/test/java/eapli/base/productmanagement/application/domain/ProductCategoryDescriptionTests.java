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

package eapli.base.productmanagement.application.domain;

import eapli.base.productmanagement.domain.ProductCategoryCode;
import eapli.base.productmanagement.domain.ProductCategoryDescription;
import org.junit.jupiter.api.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class ProductCategoryDescriptionTests {

    @Test
    public void Invalid_NullString_ShouldThrowIllegalArgumentException(){
        // Act & Assert
        assertThrows(IllegalArgumentException.class, () -> ProductCategoryDescription.valueOf(null));
    }

    @Test
    public void Invalid_EmptyString_ShouldThrowIllegalArgumentException(){
        // Act & Assert
        assertThrows(IllegalArgumentException.class, () -> ProductCategoryDescription.valueOf(""));
    }

    @Test
    public void Valid_String_ShouldReturnExpectedCodeOnInstantiatedObject(){
        // Arrange
        var expected = "TEST 1234 DESCRIPTION WITH MIN CHARACTERS";

        // Act
        var result = ProductCategoryDescription.valueOf(expected);

        // Assert
        assertEquals(expected, result.code());
    }

    @Test
    public void Invalid_DescriptionExceedsMaximumLength_ShouldThrowIllegalArgumentException(){
        // Act & Assert
        assertThrows(IllegalArgumentException.class, () -> ProductCategoryDescription.valueOf("MAXIMUMLENGTHEXCEEEDBYALOTOFCHARACTERSAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA"));
    }

    @Test
    public void Invalid_DescriptionDoesNotHaveMinimumLength_ShouldThrowIllegalArgumentException(){
        // Act & Assert
        assertThrows(IllegalArgumentException.class, () -> ProductCategoryDescription.valueOf("MINLENGTH"));
    }
}
