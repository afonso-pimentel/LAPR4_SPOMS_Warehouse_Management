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

package eapli.base.productmanagement.application;

import com.sun.istack.NotNull;
import eapli.base.productmanagement.domain.ProductCategory;
import eapli.base.productmanagement.domain.ProductCategoryCode;
import eapli.base.productmanagement.domain.ProductCategoryDescription;
import eapli.base.productmanagement.repositories.ProductCategoryRepository;
import eapli.base.usermanagement.domain.BaseRoles;
import eapli.framework.application.UseCaseController;
import eapli.framework.domain.repositories.IntegrityViolationException;
import eapli.framework.infrastructure.authz.application.AuthorizationService;

/**
 * Implementation of RegisterProductCategoryController
 */
@UseCaseController
public class RegisterProductCategoryControllerImpl implements RegisterProductCategoryController {
    private final ProductCategoryRepository repository;
    private final AuthorizationService authz;

    public RegisterProductCategoryControllerImpl(@NotNull ProductCategoryRepository repository, @NotNull AuthorizationService authzService){
        if(repository == null)
            throw new IllegalArgumentException("ProductCategoryRepository cannot be null");

        if(authzService == null)
            throw new IllegalArgumentException("AuthorizationService cannot be null");

        this.repository = repository;
        this.authz = authzService;
    }

    /**
     * {@inheritdoc}
     */
    @Override
    public void registerProductCategory(String alphaNumericCode, String description){
        this.authz.ensureAuthenticatedUserHasAnyOf(BaseRoles.POWER_USER, BaseRoles.ADMIN, BaseRoles.SALES_CLERK);

        var productCategoryCode = ProductCategoryCode.valueOf(alphaNumericCode);
        var productCategoryDescription = ProductCategoryDescription.valueOf(description);

        if(this.repository.containsOfIdentity(productCategoryCode))
            throw new IntegrityViolationException("A product category already exists with the specified alpha numeric code");

        var newProductCategory = new ProductCategory(productCategoryCode, productCategoryDescription);

        this.repository.save(newProductCategory);
    }
}
