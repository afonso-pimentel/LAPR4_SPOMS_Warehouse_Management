package eapli.base.clientusermanagement.domain;

import eapli.base.customerusermanagement.domain.*;
import eapli.framework.general.domain.model.Designation;
import eapli.framework.general.domain.model.EmailAddress;
import org.junit.jupiter.api.Test;

import java.time.Instant;
import java.util.Date;

import static com.fasterxml.jackson.databind.type.LogicalType.DateTime;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class CustomerBuilderTests {

    @Test
    public void Invalid_NoVatIdSpecified_ShouldThrowIllegalStateException(){
        // Arrange
        var billingAddress = Address.valueOf("TEST STREET NAME", "1323", "TEST CITY");
        var deliveryAddress = Address.valueOf("TEST STREET NAME", "1323", "TEST CITY");
        var emailAddress = EmailAddress.valueOf("test_email@hotmail.com");
        var phoneNumber = PhoneNumber.valueOf(913434343);
        var name = Designation.valueOf("TEST CLIENT");

        // Act & Assert
        assertThrows(IllegalStateException.class, () -> new CustomerBuilder().email(emailAddress)
                                                                            .phoneNumber(phoneNumber)
                                                                            .name(name)
                                                                            .withBillingAddress(billingAddress)
                                                                            .withDeliveryAddress(deliveryAddress)
                                                                            .build());
    }

    @Test
    public void Invalid_NoNameSpecified_ShouldThrowIllegalStateException(){
        // Arrange
        var vatCode = VatCode.valueOf("2534839833");
        var billingAddress = Address.valueOf("TEST STREET NAME", "1323", "TEST CITY");
        var deliveryAddress = Address.valueOf("TEST STREET NAME", "1323", "TEST CITY");
        var emailAddress = EmailAddress.valueOf("test_email@hotmail.com");
        var phoneNumber = PhoneNumber.valueOf(913434343);

        // Act & Assert
        assertThrows(IllegalStateException.class, () -> new CustomerBuilder().identifiedBy(vatCode)
                                                                            .email(emailAddress)
                                                                            .phoneNumber(phoneNumber)
                                                                            .withBillingAddress(billingAddress)
                                                                            .withDeliveryAddress(deliveryAddress)
                                                                            .build());
    }

    @Test
    public void Invalid_NoEmailSpecified_ShouldThrowIllegalStateException(){
        // Arrange
        var vatCode = VatCode.valueOf("2534839833");
        var billingAddress = Address.valueOf("TEST STREET NAME", "1323", "TEST CITY");
        var deliveryAddress = Address.valueOf("TEST STREET NAME", "1323", "TEST CITY");
        var phoneNumber = PhoneNumber.valueOf(913434343);
        var name = Designation.valueOf("TEST CLIENT");

        // Act & Assert
        assertThrows(IllegalStateException.class, () -> new CustomerBuilder().identifiedBy(vatCode)
                .phoneNumber(phoneNumber)
                .name(name)
                .withBillingAddress(billingAddress)
                .withDeliveryAddress(deliveryAddress)
                .build());
    }

    @Test
    public void Invalid_NoPhoneNumberSpecified_ShouldThrowIllegalStateException(){
        // Arrange
        var vatCode = VatCode.valueOf("2534839833");
        var billingAddress = Address.valueOf("TEST STREET NAME", "1323", "TEST CITY");
        var deliveryAddress = Address.valueOf("TEST STREET NAME", "1323", "TEST CITY");
        var emailAddress = EmailAddress.valueOf("test_email@hotmail.com");
        var name = Designation.valueOf("TEST CLIENT");

        // Act & Assert
        assertThrows(IllegalStateException.class, () -> new CustomerBuilder().identifiedBy(vatCode)
                .email(emailAddress)
                .name(name)
                .withBillingAddress(billingAddress)
                .withDeliveryAddress(deliveryAddress)
                .build());
    }

    @Test
    public void Valid_CustomerArguments_ShouldReturnExpectedObject(){
        // Arrange
        var expectedVatCode = VatCode.valueOf("2534839833");
        var expectedBillingAddress = Address.valueOf("TEST STREET NAME", "1323", "TEST CITY");
        var expectedDeliveryAddress = Address.valueOf("TEST STREET NAME", "1323", "TEST CITY");
        var expectedEmailAddress = EmailAddress.valueOf("test_email@hotmail.com");
        var expectedPhoneNumber = PhoneNumber.valueOf(913434343);
        var expectedName = Designation.valueOf("TEST CLIENT");
        var expectedBirthDate = BirthDate.valueOf(Date.from(Instant.parse("2022-02-02T00:00:00.000Z")));
        var expectedGender = Gender.valueOf("Male");

        // Act
        Customer response = new CustomerBuilder().identifiedBy(expectedVatCode)
                .email(expectedEmailAddress)
                .name(expectedName)
                .phoneNumber(expectedPhoneNumber)
                .withBillingAddress(expectedBillingAddress)
                .withDeliveryAddress(expectedDeliveryAddress)
                .withBirthDate(expectedBirthDate)
                .withGender(expectedGender)
                .build();

        // Assert
        assertEquals(expectedVatCode,  response.identity());
        assertEquals(expectedBillingAddress, response.billingAddress());
        assertEquals(expectedDeliveryAddress, response.deliveryAddress());
        assertEquals(expectedEmailAddress, response.email());
        assertEquals(expectedPhoneNumber, response.phoneNumber());
        assertEquals(expectedName, response.name());
        assertEquals(expectedBirthDate, response.birthDate());
        assertEquals(expectedGender, response.gender());
    }
}
