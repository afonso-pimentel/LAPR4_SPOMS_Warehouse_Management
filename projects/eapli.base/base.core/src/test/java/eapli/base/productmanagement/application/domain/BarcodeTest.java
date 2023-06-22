package eapli.base.productmanagement.application.domain;

import eapli.base.productmanagement.domain.Barcode;
import org.junit.jupiter.api.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.*;

class BarcodeTest {

    @Test
    public void Invalid_BarcodeIsNotTheRightLength_ShouldThrowIllegalArgumentException(){
        // Act & Assert
        assertThrows(IllegalArgumentException.class, () -> Barcode.valueOf("123",new byte[1]));
    }

    @Test
    public void Invalid_BarcodeIsNotNumeric_ShouldThrowIllegalArgumentException(){
        // Act & Assert
        assertThrows(IllegalArgumentException.class, () -> Barcode.valueOf("ABCDEFGHIJKLM",new byte[1]));
    }

    @Test
    public void Invalid_NullBarcodeImage_ShouldThrowIllegalStateException(){
        // Act & Assert
        assertThrows(IllegalArgumentException.class, () -> Barcode.valueOf("ABCDEFGHIJKLM",null));
    }

    @Test
    public void Invalid_NullBarcode_ShouldThrowIllegalStateException(){
        // Act & Assert
        assertThrows(IllegalArgumentException.class, () -> Barcode.valueOf(null,new byte[1]));
    }

    @Test
    public void Valid_Barcode_ShouldReturnExpectedCodeOnInstantiatedObject(){

        // Arrange
        var expectedCode = "1234567890000";
        // Act
        var resultBarcode = Barcode.valueOf("1234567890000",new byte[1]);
        // Assert
        assertEquals(expectedCode,resultBarcode.toString());
    }

}