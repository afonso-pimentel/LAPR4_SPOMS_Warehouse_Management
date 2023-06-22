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

import eapli.base.productmanagement.domain.ProductCategory;
import eapli.base.productmanagement.domain.ProductCategoryCode;
import eapli.base.productmanagement.domain.ProductCategoryDescription;
import eapli.framework.general.domain.model.Description;
import org.junit.jupiter.api.Test;
import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class ProductCategoryTests {

    @Test
    public void Invalid_NullAlphaCode_ShouldThrowException(){
        // Assert
        assertThrows(IllegalArgumentException.class, () -> new ProductCategory(null, ProductCategoryDescription.valueOf("TEST")));
    }

    @Test
    public void Invalid_NullDescription_ShouldThrowException(){
        // Assert
        assertThrows(IllegalArgumentException.class, () -> new ProductCategory(ProductCategoryCode.valueOf("TEST"), null));
    }

    @Test
    public void Valid_Arguments_ShouldReturnExpectedObject(){
        // Arrange
        var expectedAlphaNumericCode = ProductCategoryCode.valueOf("TEST");
        var expectedDescription = ProductCategoryDescription.valueOf("TEST DESCRIPTION FOR PRODUCT CATEGORY");

        // Act
        var result = new ProductCategory(expectedAlphaNumericCode, expectedDescription);

        // Assert
        assertEquals(expectedAlphaNumericCode, result.code());
        assertEquals(expectedDescription, result.description());
    }
}
