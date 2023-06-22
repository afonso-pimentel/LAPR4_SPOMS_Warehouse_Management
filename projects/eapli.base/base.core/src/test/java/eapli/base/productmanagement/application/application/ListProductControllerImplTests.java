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

import eapli.base.productmanagement.application.ListProductControllerImpl;
import eapli.base.productmanagement.application.ListProductService;
import eapli.base.productmanagement.domain.Product;
import eapli.base.productmanagement.repositories.ProductRepository;
import eapli.base.usermanagement.domain.BaseRoles;
import eapli.framework.infrastructure.authz.application.UserSession;
import eapli.framework.infrastructure.authz.application.exceptions.UnauthorizedException;
import eapli.framework.infrastructure.authz.application.AuthorizationService;
import eapli.framework.infrastructure.authz.domain.model.*;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.isNull;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class ListProductControllerImplTests {
    @Mock
    private ProductRepository mockProductRepository;
    @Mock
    private AuthorizationService mockAuthorizationService;
    @Mock
    private ListProductService mockListProductService;

    private ListProductControllerImpl productController;

    @BeforeEach
    public void createMocks() {
        MockitoAnnotations.openMocks(this);
        productController = new ListProductControllerImpl(mockAuthorizationService, mockProductRepository, mockListProductService);
    }

    @Test
    public void valid_OnNullParamsCallServiceListProductController(){
        doNothing().when(mockAuthorizationService).ensureAuthenticatedUserHasAnyOf(any(Role.class));

        Iterable<Product> expected = new ArrayList()
        {
            {add(new Product());}
            {add(new Product());}
        };

        when(mockListProductService.allProducts()).thenReturn(expected);

        Iterable<Product> result = productController.sortAndFilterProducts(null,null,null,true);

        assertEquals(expected, result);

        verify(mockAuthorizationService).ensureAuthenticatedUserHasAnyOf(any(Role.class));
        verify(mockListProductService).allProducts();
    }

    @Test
    public void valid_CallRepositoryListProductController(){
        doNothing().when(mockAuthorizationService).ensureAuthenticatedUserHasAnyOf(any(Role.class));

        Iterable<Product> expected = new ArrayList()
        {
            {add(new Product());}
            {add(new Product());}
        };

        when(mockProductRepository.filterSortActive(anyString(),anyString(),anyString(),anyBoolean())).thenReturn(expected);

        Iterable<Product> result = productController.sortAndFilterProducts("Dummy","Dummy","Dummy",true);

        assertEquals(expected, result);

        verify(mockAuthorizationService).ensureAuthenticatedUserHasAnyOf(any(Role.class));
        verify(mockProductRepository).filterSortActive(anyString(),anyString(),anyString(),anyBoolean());
    }

    @Test
    public void valid_OnlyFilterProductController(){
        doNothing().when(mockAuthorizationService).ensureAuthenticatedUserHasAnyOf(any(Role.class));

        Iterable<Product> expected = new ArrayList()
        {
            {add(new Product());}
            {add(new Product());}
        };

        when(mockProductRepository.filterSortActive(anyString(),anyString(),isNull(), any())).thenReturn(expected);

        Iterable<Product> result = productController.sortAndFilterProducts("Dummy","Dummy",null, true);

        assertEquals(expected, result);

        verify(mockAuthorizationService).ensureAuthenticatedUserHasAnyOf(any(Role.class));
        verify(mockProductRepository).filterSortActive(anyString(),anyString(),isNull(),any());
    }

    @Test
    public void valid_OnlySortProductController(){
        doNothing().when(mockAuthorizationService).ensureAuthenticatedUserHasAnyOf(any(Role.class));

        Iterable<Product> expected = new ArrayList()
        {
            {add(new Product());}
            {add(new Product());}
        };

        when(mockProductRepository.filterSortActive(isNull(),isNull(), anyString(), any())).thenReturn(expected);

        Iterable<Product> result = productController.sortAndFilterProducts(null,null,"Dummy", true);

        assertEquals(expected, result);

        verify(mockAuthorizationService).ensureAuthenticatedUserHasAnyOf(any(Role.class));
        verify(mockProductRepository).filterSortActive(isNull(),isNull(), anyString(), any());
    }
}
