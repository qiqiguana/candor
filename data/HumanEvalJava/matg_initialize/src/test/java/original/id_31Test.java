package original;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
/**
* Test class of IsPrime.
*/
class IsPrimeTest {
    @Test
    void isPrime_shouldReturnFalse_WhenInputIsLessThanOrEqualTo1() {
        // Arrange and Act
        boolean result = IsPrime.isPrime(1);
        // Assert
        assertFalse(result);
    }
}