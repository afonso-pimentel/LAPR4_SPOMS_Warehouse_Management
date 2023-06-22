package eapli.base.clientusermanagement.application;

import eapli.base.customerusermanagement.application.RegisterCustomerControllerImpl;
import eapli.base.customerusermanagement.domain.*;
import eapli.base.customerusermanagement.repositories.CustomerRepository;
import eapli.framework.domain.repositories.IntegrityViolationException;
import eapli.framework.general.domain.model.Designation;
import eapli.framework.general.domain.model.EmailAddress;
import eapli.framework.infrastructure.authz.application.AuthorizationService;
import eapli.framework.infrastructure.authz.domain.model.Role;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import java.util.Optional;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class RegisterCustomerControllerImplTests {
    @Mock
    private CustomerRepository mockCustomerRepository;

    @Mock
    private AuthorizationService mockAuthorizationService;

    private RegisterCustomerControllerImpl registerCustomerController;

    @BeforeEach
    public void createMocks() {
        MockitoAnnotations.openMocks(this);
        registerCustomerController = new RegisterCustomerControllerImpl(mockCustomerRepository, mockAuthorizationService);
    }

    @Test
    public void Invalid_NullProductCategoryRepository_ShouldThrowIllegalArgumentException(){
        // Act & Assert
        assertThrows(IllegalArgumentException.class, () -> new RegisterCustomerControllerImpl(null, mockAuthorizationService));
    }

    @Test
    public void Invalid_NullAuthorizationService_ShouldThrowIllegalArgumentException(){
        // Act & Assert
        assertThrows(IllegalArgumentException.class, () -> new RegisterCustomerControllerImpl(mockCustomerRepository, null));
    }

    @Test
    public void Valid_NonExistentCustomer_ShouldInsertNewProductCategoryAndNotThrowException(){
        // Arrange
        var expectedVatCode = VatCode.valueOf("2534839833");
        var expectedPhoneNumber = PhoneNumber.valueOf(913434343);
        var expectedName = Designation.valueOf("TEST CLIENT");
        var expectedEmail = EmailAddress.valueOf("test_email@hotmail.com");

        Optional<Customer> expectedRepositoryAnswer = Optional.empty();

        doNothing().when(mockAuthorizationService).ensureAuthenticatedUserHasAnyOf(any(Role.class));
        when(mockCustomerRepository.findEquals(any(VatCode.class), any(PhoneNumber.class), any(EmailAddress.class))).thenReturn(expectedRepositoryAnswer);

        // Act & Assert
        assertDoesNotThrow(() -> registerCustomerController.registerCustomer(expectedVatCode.toString(), expectedName.toString(),
                                                                             expectedEmail.toString(), expectedPhoneNumber.toString()));

        // Assert
        verify(mockAuthorizationService).ensureAuthenticatedUserHasAnyOf(any(Role.class));
        verify(mockCustomerRepository).findEquals(any(VatCode.class), any(PhoneNumber.class), any(EmailAddress.class));
        verify(mockCustomerRepository).save(any(Customer.class));
    }


    @Test
    public void Invalid_ExistentCustomer_ShouldInsertNewProductCategoryAndNotThrowException(){
        // Arrange
        var expectedVatCode = VatCode.valueOf("2534839833");
        var expectedPhoneNumber = PhoneNumber.valueOf(913434343);
        var expectedName = Designation.valueOf("TEST CLIENT");
        var expectedEmail = EmailAddress.valueOf("test_email@hotmail.com");

        var customer = new Customer(expectedVatCode, expectedName, expectedEmail, expectedPhoneNumber, null, null, null, null);

        Optional<Customer> expectedRepositoryAnswer = Optional.of(customer);

        doNothing().when(mockAuthorizationService).ensureAuthenticatedUserHasAnyOf(any(Role.class));
        when(mockCustomerRepository.findEquals(any(VatCode.class), any(PhoneNumber.class), any(EmailAddress.class))).thenReturn(expectedRepositoryAnswer);

        // Act & Assert
        assertThrows(IntegrityViolationException.class, () -> registerCustomerController.registerCustomer(expectedVatCode.toString(), expectedName.toString(),
                expectedEmail.toString(), expectedPhoneNumber.toString()));

        // Assert
        verify(mockAuthorizationService).ensureAuthenticatedUserHasAnyOf(any(Role.class));
        verify(mockCustomerRepository).findEquals(any(VatCode.class), any(PhoneNumber.class), any(EmailAddress.class));
        verify(mockCustomerRepository, times(0)).save(any(Customer.class));
    }
}
