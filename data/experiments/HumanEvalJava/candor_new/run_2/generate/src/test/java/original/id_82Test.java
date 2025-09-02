package original;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
/**
* Test class of PrimeLength.
*/
class PrimeLengthTest {
    @Test
    void testPrimeLength_ReturnsTrue_WhenStringLengthIsAPrimeNumber() {
        // Arrange and Act
        boolean result = PrimeLength.primeLength("Hello");
        // Assert
        assertTrue(result);
    }
}