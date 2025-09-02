package original;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
/**
* Test class of CheckIfLastCharIsALetter.
*/
class CheckIfLastCharIsALetterTest {
    @Test
    void checkIfLastCharIsALetter_ReturnsTrue_WhenSingleLetter() {
        // Arrange and Act
        Boolean result = CheckIfLastCharIsALetter.checkIfLastCharIsALetter("A");
        // Assert
        assertTrue(result);
    }
}