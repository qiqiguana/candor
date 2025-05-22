package original;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
/**
* Test class of CheckIfLastCharIsALetter.
*/
class CheckIfLastCharIsALetterTest {
    @Test
    void checkIfLastCharIsALetter_WhenStringIsEmpty_ReturnsFalse() {
        // Arrange and Act
        Boolean result = CheckIfLastCharIsALetter.checkIfLastCharIsALetter("");
        // Assert
        assertFalse(result);
    }
}