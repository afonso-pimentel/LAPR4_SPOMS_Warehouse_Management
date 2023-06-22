/*
 * Copyright (c) 2021-2022 the original author or authors.
 *
 * MIT License
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy of this software and
 * associated documentation files (the "Software"), to deal in the Software without restriction,
 * including without limitation the rights to use, copy, modify, merge, publish, distribute,
 * sublicense, and/or sell copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all copies or
 * substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT
 * NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND
 * NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM,
 * DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */
package eapli.base.infrastructure.bootstrapers.demo;

import eapli.base.infrastructure.persistence.PersistenceContext;
import eapli.base.productmanagement.application.RegisterProductCategoryController;
import eapli.base.productmanagement.application.RegisterProductCategoryControllerImpl;
import eapli.framework.actions.Action;
import eapli.framework.domain.repositories.ConcurrencyException;
import eapli.framework.domain.repositories.IntegrityViolationException;
import eapli.framework.infrastructure.authz.application.AuthzRegistry;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ProductCategoriesBootstrapper implements Action {
    private static final Logger LOGGER = LoggerFactory.getLogger(
            ProductCategoriesBootstrapper.class);

    private final RegisterProductCategoryController registerProductCategoryController;

    public ProductCategoriesBootstrapper(){
        var productCategoriesRepository = PersistenceContext.repositories().productCategories();

        this.registerProductCategoryController = new RegisterProductCategoryControllerImpl(productCategoriesRepository, AuthzRegistry.authorizationService());

    }

    @Override
    public boolean execute() {
        registerProductCategory("T-SHIRTS", "All types of t-shirts from all brands");
        registerProductCategory("JEANS", "All types of jeans from all brands");
        registerProductCategory("JACKETS", "All types of jackets all brands");
        registerProductCategory("HOODIES", "All types of hoodies from all brands");

        return true;
    }

    private void registerProductCategory(final String alphaNumericCode, final String description){
        try {
            this.registerProductCategoryController.registerProductCategory(alphaNumericCode, description);

        } catch (final ConcurrencyException | IntegrityViolationException e) {
            // ignoring exception. assuming it is just a primary key violation
            // due to the tentative of inserting a duplicated user
            LOGGER.warn("Assuming {} already exists (activate trace log for details)", alphaNumericCode);
            LOGGER.trace("Assuming existing record", e);
        }
    }
}
