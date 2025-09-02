package original;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
/**
* Test class of IsPrime.
*/
class IsPrimeTest {
    @Test
    void testIsPrimeForCompositeNumbers() {
        // Arrange and Act
        boolean result = IsPrime.isPrime(6);
        
        // Assert
        assertFalse(result);
    }
}