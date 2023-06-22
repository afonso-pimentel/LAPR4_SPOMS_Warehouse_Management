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

package eapli.base.app.backoffice.console.presentation.product;

import com.sun.istack.NotNull;
import eapli.base.productmanagement.application.RegisterProductCategoryController;
import eapli.framework.domain.repositories.IntegrityViolationException;
import eapli.framework.io.util.Console;
import eapli.framework.presentation.console.AbstractUI;
import eapli.framework.strings.util.StringPredicates;

/**
 * UI for registering a product category to the application.
 */
public class RegisterProductCategoryUI extends AbstractUI {
    private RegisterProductCategoryController controller;

    public RegisterProductCategoryUI(@NotNull RegisterProductCategoryController registerProductCategoryController){
        this.controller = registerProductCategoryController;
    }

    @Override
    protected boolean doShow() {
        final String alphaNumericCode = Console.readLine("Alpha Numeric Code");

        if (StringPredicates.isNullOrEmpty(alphaNumericCode)) {
            System.out.println("Alpha numeric code cannot be null or empty.");
            return false;
        }

        final String description = Console.readLine("Description");

        if (StringPredicates.isNullOrEmpty(description)) {
            System.out.println("Description cannot be null or empty.");
            return false;
        }

        try {
            this.controller.registerProductCategory(alphaNumericCode, description);
            System.out.printf("Product category with alpha numeric code %s has been successfully added.\n", alphaNumericCode);
        } catch (final IntegrityViolationException e) {
                System.out.printf("Alpha numeric code %s is already in use.\n", alphaNumericCode);
        } catch(final IllegalArgumentException e){
            System.out.printf("Invalid value sent: %s\n", e.getMessage());
        }

        return false;
    }

    @Override
    public String headline() {
        return "Register Product Category";
    }
}
