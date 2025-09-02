package original;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
/**
* Test class of IsPrime.
*/
class IsPrimeTest {
    @Test
    void isPrime_WhenInputIsLessThanOrEqualTo1_ReturnsFalse() {
        // Arrange and Act
        Boolean result = IsPrime.isPrime(0);
        // Assert
        assertFalse(result);
    }
}