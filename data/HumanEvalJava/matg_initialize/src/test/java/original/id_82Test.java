package original;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
/**
* Test class of PrimeLength.
*/
class PrimeLengthTest {
    @Test
    void testPrimeLength_EmptyString_ReturnsFalse() {
        // Arrange
        String input = "";
        boolean expectedOutput = false;

        // Act
        boolean actualOutput = PrimeLength.primeLength(input);

        // Assert
        assertEquals(expectedOutput, actualOutput);
    }
}
