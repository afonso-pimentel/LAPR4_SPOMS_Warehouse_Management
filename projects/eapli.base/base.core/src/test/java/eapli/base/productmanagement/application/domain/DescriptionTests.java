package eapli.base.productmanagement.application.domain;

import eapli.base.productmanagement.domain.ShortDescription;
import eapli.base.productmanagement.domain.LongDescription;
import org.junit.jupiter.api.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class DescriptionTests {

    @Test
    public void Invalid_NullString_ShouldThrowIllegalArgumentException(){
        // Act & Assert
        assertThrows(IllegalArgumentException.class, () -> ShortDescription.valueOf(null));
        assertThrows(IllegalArgumentException.class, () -> LongDescription.valueOf(null));
    }

    @Test
    public void Invalid_EmptyString_ShouldThrowIllegalArgumentException(){
        // Act & Assert
        assertThrows(IllegalArgumentException.class, () -> ShortDescription.valueOf(""));
        assertThrows(IllegalArgumentException.class, () -> LongDescription.valueOf(""));
    }

    @Test
    public void Valid_String_ShouldReturnExpectedCodeOnInstantiatedObject(){
        // Arrange
        var expectedShort = "SHORT";
        var expectedLong = "THIS IS A LONG DESCRIPTION FOR ME TO TEST ";
        // Act
        var resultShort = ShortDescription.valueOf(expectedShort);
        var resultLong = LongDescription.valueOf(expectedLong);

        // Assert
        assertEquals(expectedShort, resultShort.code());
        assertEquals(expectedLong, resultLong.code());
    }

    @Test
    public void Invalid_DescriptionExceedsMaximumLength_ShouldThrowIllegalArgumentException(){
        // Act & Assert
        assertThrows(IllegalArgumentException.class, () -> ShortDescription.valueOf("MAXIMUMLENGTHEXCEEEDBYALOTOFCHARACTERSAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA"));
    }

    @Test
    public void Invalid_DescriptionDoesNotHaveMinimumLength_ShouldThrowIllegalArgumentException(){
        // Act & Assert
        assertThrows(IllegalArgumentException.class, () -> LongDescription.valueOf("MINLENGTH"));
    }
}
