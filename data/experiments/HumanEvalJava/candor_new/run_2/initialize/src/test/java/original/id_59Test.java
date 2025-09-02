package original;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
/**
* Test class of LargestPrimeFactor.
*/
class LargestPrimeFactorTest {
    @Test
    void testLargestPrimeFactor_SimpleCase() {
        // Arrange and Act
        int result = LargestPrimeFactor.largestPrimeFactor(2048);
        // Assert
        assertEquals(2, result);
    }
}
