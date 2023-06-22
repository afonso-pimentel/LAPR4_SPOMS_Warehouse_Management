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
import eapli.base.productmanagement.application.ListProductCategoryService;
import eapli.base.productmanagement.application.RegisterProductController;
import eapli.base.productmanagement.application.RegisterProductControllerImpl;
import eapli.base.productmanagement.domain.ProductCategory;
import eapli.base.productmanagement.domain.ProductCategoryCode;
import eapli.base.productmanagement.domain.ProductCategoryDescription;
import eapli.framework.actions.Action;
import eapli.framework.domain.repositories.ConcurrencyException;
import eapli.framework.domain.repositories.IntegrityViolationException;
import eapli.framework.infrastructure.authz.application.AuthzRegistry;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ProductsBootstrapper implements Action {
    private static final Logger LOGGER = LoggerFactory.getLogger(
            ProductsBootstrapper.class);

    private final RegisterProductController registerProductController;

    public ProductsBootstrapper(){
        var productRepository = PersistenceContext.repositories().products();

        var productCategoriesRepository = PersistenceContext.repositories().productCategories();

        var listProductCategoryService = new ListProductCategoryService(productCategoriesRepository);

        this.registerProductController = new RegisterProductControllerImpl(productRepository, listProductCategoryService, AuthzRegistry.authorizationService());
    }

    @Override
    public boolean execute() {
        registerProduct("LIONT", "Lion of Porches", "LIONT_WHITE",
                            new ProductCategory(ProductCategoryCode.valueOf("T-SHIRTS"), ProductCategoryDescription.valueOf("All types of t-shirts from all brands")),
                    "5978020137962", new byte[1], 20, "T-Shirt","Lion of Porches T shirt with LP logo", 1L, 1L, 2L);

        registerProduct("LIONJ", "Lion of Porches", "LIONJ_WHITE",
                new ProductCategory(ProductCategoryCode.valueOf("JEANS"), ProductCategoryDescription.valueOf("All types of jeans from all brands")),
                "3380201355612", new byte[1], 20, "Jeans","Lion of Porches Jeans with a patches in the front", 1L, 2L, 2L);


        registerProduct("LIONJA", "Lion of Porches", "LIONJA_WHITE",
                new ProductCategory(ProductCategoryCode.valueOf("JACKETS"), ProductCategoryDescription.valueOf("All types of jackets from all brands")),
                "1880201379642", new byte[1], 20, "Jacket","Lion of Porches Jacket with LP logo", 2L, 3L, 3L);

        registerProduct("LIONH", "Lion of Porches", "LIONH_WHITE",
                new ProductCategory(ProductCategoryCode.valueOf("HOODIES"), ProductCategoryDescription.valueOf("All types of hoodies from all brands")),
                "7558020137966", new byte[1], 20, "Hoddie","Lion of Porches Hoodie with LP logo", 3L, 2L, 4L);

        return true;
    }

    private void registerProduct(final String internalCode, final String brand, final String reference, final ProductCategory category,
                                 final String barcode, final byte[] bars, final double price, final String shortDescription, final String extendedDescription,final Long aisle, final Long row, final Long shelf){
        try {
            this.registerProductController.registerProduct(internalCode, brand,
                                        reference, category, barcode, bars, price, shortDescription, extendedDescription, null, null, aisle, row, shelf, null ,100L);

        } catch (final ConcurrencyException | IntegrityViolationException e) {
            // ignoring exception. assuming it is just a primary key violation
            // due to the tentative of inserting a duplicated user
            LOGGER.warn("Assuming {} already exists (activate trace log for details)", internalCode);
            LOGGER.trace("Assuming existing record", e);
        }
    }
}
