package original;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
/**
* Test class of IsPrime.
*/
class IsPrimeTest {
    @Test
    void testIsPrime_withNumber11_ReturnTrue() {
        // Arrange and Act
        Boolean result = IsPrime.isPrime(11);
        // Assert
        assertTrue(result);
    }
}