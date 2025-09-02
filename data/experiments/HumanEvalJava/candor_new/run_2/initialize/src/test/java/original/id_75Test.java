package original;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
/**
* Test class of IsMultiplyPrime.
*/
class IsMultiplyPrimeTest {
    @Test
    void testIsMultiplyPrime_When30_ReturnsTrue() {
        // Given
        int number = 30;
        boolean expected = true;

        // When
        boolean result = IsMultiplyPrime.isMultiplyPrime(number);

        // Then
        assertEquals(expected, result);
    }
}