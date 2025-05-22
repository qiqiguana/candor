package original;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
/**
* Test class of IsMultiplyPrime.
*/
class IsMultiplyPrimeTest {
    @Test
    void testIsMultiplyPrime_WhenCalledWith30_ReturnsTrue() {
        // Arrange and Act
        boolean result = IsMultiplyPrime.isMultiplyPrime(30);
        // Assert
        assertTrue(result);
    }
}