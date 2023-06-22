package eapli.base.productmanagement.application.application;

import eapli.base.productmanagement.application.ListProductCategoryService;
import eapli.base.productmanagement.application.RegisterProductControllerImpl;
import eapli.base.productmanagement.domain.*;
import eapli.base.productmanagement.repositories.ProductRepository;
import eapli.framework.domain.repositories.IntegrityViolationException;
import eapli.framework.infrastructure.authz.application.AuthorizationService;
import eapli.framework.infrastructure.authz.domain.model.Role;
import org.apache.commons.io.IOUtils;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import java.io.FileInputStream;
import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class RegisterProductControllerImplTest {
    @Mock
    private ProductRepository mockProductRepository;

    @Mock
    private ListProductCategoryService mockListCategories;

    @Mock
    private AuthorizationService mockAuthorizationService;

    private RegisterProductControllerImpl productController;

    @BeforeEach
    public void createMocks(){
        MockitoAnnotations.openMocks(this);
        productController = new RegisterProductControllerImpl(mockProductRepository, mockListCategories, mockAuthorizationService);
    }

    @Test
    public void Invalid_NullProductRepository_ShouldThrowIllegalArgumentException(){
        //Act & Assert
        assertThrows(IllegalArgumentException.class, () -> new RegisterProductControllerImpl(null, mockListCategories, mockAuthorizationService));
    }

    @Test
    public void Invalid_NullListCategories_ShouldThrowIllegalArgumentException(){
        //Act & Assert
        assertThrows(IllegalArgumentException.class, () -> new RegisterProductControllerImpl(mockProductRepository, null, mockAuthorizationService));
    }

    @Test
    public void Invalid_NullAuthorizationService_ShouldThrowIllegalArgumentException(){
        //Act & Assert
        assertThrows(IllegalArgumentException.class, () -> new RegisterProductControllerImpl(mockProductRepository, mockListCategories, null));
    }

    /*
    @Test
    public void Valid_NonExistentInternalCode_ShouldInsertNewProductAndNotThrowException() throws IOException {
        //Arrange
        var internalCode = "1234A5678";
        var brand = "LG";
        var reference = "XPTO";
        var category = new ProductCategory(ProductCategoryCode.valueOf("CATEGORY12"), ProductCategoryDescription.valueOf("TEST NEW PRODUCT CATEGORY"));
        var barcode = "1234567890000";
        var barcodePath = "/main/resources/barcode_example.jfif";
        var barcodeStream = this.getClass().getClassLoader().getResourceAsStream(barcodePath);
        var bars = IOUtils.toByteArray(barcodeStream);
        var price = 100;
        var shortDescription = "shortDescription";
        var extendedDescription = "extendedDescription with over 20 characters.";

        doNothing().when(mockAuthorizationService).ensureAuthenticatedUserHasAnyOf(any(Role.class));
        when(mockProductRepository.containsOfIdentity(any(AlphaNumericCode.class))).thenReturn(false);

        //Act & Assert
        assertDoesNotThrow(() -> productController.registerProduct(internalCode,brand,reference,category,barcode,bars,price, shortDescription, extendedDescription));

        //Assert
        verify(mockAuthorizationService).ensureAuthenticatedUserHasAnyOf(any(Role.class));
        verify(mockProductRepository).containsOfIdentity(any(AlphaNumericCode.class));
        verify(mockProductRepository).save(any());
    }*/
    /*
    @Test
    public void Invalid_ExistentInternalCode_ShouldThrowIntegrityViolationException() throws IOException {
        //Arrange
        var internalCode = "1234A5678";
        var brand = "LG";
        var reference = "XPTO";
        var category = new ProductCategory(ProductCategoryCode.valueOf("CATEGORY12"), ProductCategoryDescription.valueOf("TEST NEW PRODUCT CATEGORY"));
        var barcode = "1234567890000";
        var barcodePath = System.getProperty("user.dir") + "\\src\\main\\resources\\barcode_example.jfif";
        var barcodeStream = new FileInputStream(barcodePath);
        var bars = IOUtils.toByteArray(barcodeStream);
        var price = 100;
        var shortDescription = "shortDescription";
        var extendedDescription = "extendedDescription with over 20 characters.";

        doNothing().when(mockAuthorizationService).ensureAuthenticatedUserHasAnyOf(any(Role.class));
        when(mockProductRepository.containsOfIdentity(any(AlphaNumericCode.class))).thenReturn(true);

        //Act & Assert
        assertThrows(IntegrityViolationException.class,() -> productController.registerProduct(internalCode,brand,reference,category,barcode,bars,price, shortDescription, extendedDescription));

        //Assert
        verify(mockAuthorizationService).ensureAuthenticatedUserHasAnyOf(any(Role.class));
        verify(mockProductRepository).containsOfIdentity(any(AlphaNumericCode.class));
    }*/
}