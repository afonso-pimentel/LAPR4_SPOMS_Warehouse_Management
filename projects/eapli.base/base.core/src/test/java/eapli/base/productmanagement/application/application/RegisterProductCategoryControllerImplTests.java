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

package eapli.base.productmanagement.application.application;

import eapli.base.productmanagement.application.RegisterProductCategoryControllerImpl;
import eapli.base.productmanagement.domain.ProductCategory;
import eapli.base.productmanagement.domain.ProductCategoryCode;
import eapli.base.productmanagement.repositories.ProductCategoryRepository;
import eapli.framework.domain.repositories.IntegrityViolationException;
import eapli.framework.infrastructure.authz.application.AuthorizationService;
import eapli.framework.infrastructure.authz.domain.model.Role;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class RegisterProductCategoryControllerImplTests {
    @Mock
    private ProductCategoryRepository mockProductCategoryRepository;

    @Mock
    private AuthorizationService mockAuthorizationService;

    private RegisterProductCategoryControllerImpl productCategoryController;

    @BeforeEach
    public void createMocks() {
        MockitoAnnotations.openMocks(this);
        productCategoryController = new RegisterProductCategoryControllerImpl(mockProductCategoryRepository, mockAuthorizationService);
    }

    @Test
    public void Invalid_NullProductCategoryRepository_ShouldThrowIllegalArgumentException(){
        // Act & Assert
        assertThrows(IllegalArgumentException.class, () -> new RegisterProductCategoryControllerImpl(null, mockAuthorizationService));
    }

    @Test
    public void Invalid_NullAuthorizationService_ShouldThrowIllegalArgumentException(){
        // Act & Assert
        assertThrows(IllegalArgumentException.class, () -> new RegisterProductCategoryControllerImpl(mockProductCategoryRepository, null));
    }

    @Test
    public void Valid_NonExistentAlphaCodeForProductCategory_ShouldInsertNewProductCategoryAndNotThrowException(){
        // Arrange
        var alphaCode = "TESTCAT";
        var description = "TEST NEW PRODUCT CATEGORY";

        doNothing().when(mockAuthorizationService).ensureAuthenticatedUserHasAnyOf(any(Role.class));
        when(mockProductCategoryRepository.containsOfIdentity(any(ProductCategoryCode.class))).thenReturn(Boolean.FALSE);

        // Act & Assert
        assertDoesNotThrow(() -> productCategoryController.registerProductCategory(alphaCode, description));

        // Assert
        verify(mockAuthorizationService).ensureAuthenticatedUserHasAnyOf(any(Role.class));
        verify(mockProductCategoryRepository).containsOfIdentity(any(ProductCategoryCode.class));
        verify(mockProductCategoryRepository).save(any(ProductCategory.class));
    }

    @Test
    public void Invalid_ExistentAlphaCodeForProductCategory_ShouldThrowIntegrityViolationException(){
        // Arrange
        var alphaCode = "TESTCAT";
        var description = "TEST NEW PRODUCT CATEGORY";

        doNothing().when(mockAuthorizationService).ensureAuthenticatedUserHasAnyOf(any(Role.class));
        when(mockProductCategoryRepository.containsOfIdentity(any(ProductCategoryCode.class))).thenReturn(Boolean.TRUE);

        // Act & Assert
        assertThrows(IntegrityViolationException.class, () -> productCategoryController.registerProductCategory(alphaCode, description));

        // Assert
        verify(mockAuthorizationService).ensureAuthenticatedUserHasAnyOf(any(Role.class));
        verify(mockProductCategoryRepository).containsOfIdentity(any(ProductCategoryCode.class));
    }
}
