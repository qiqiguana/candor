package original;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertFalse;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
/**
* Test class of IsPrime.
*/
class IsPrimeTest {
    @Test
    void testIsPrime_ForNumberLessThanOrEqualTo1_ReturnsFalse() {
        // Arrange and Act
        boolean result = IsPrime.isPrime(1);
        // Assert
        assertFalse(result);
    }
    @Test
    public void testIsPrimeLessThanOrEqualToOne() {
        assertFalse(IsPrime.isPrime(0));
        assertFalse(IsPrime.isPrime(-10));
        assertFalse(IsPrime.isPrime(1));
    }
    @Test
    public void testIsPrimeForPrimes() {
        assertTrue(IsPrime.isPrime(101));
        assertTrue(IsPrime.isPrime(5));
        assertTrue(IsPrime.isPrime(11));
    }
    @Test
    public void testIsPrimeForComposites() {
        assertFalse(IsPrime.isPrime(6));
        assertFalse(IsPrime.isPrime(4));
        assertFalse(IsPrime.isPrime(85));
    }
    @Test
    public void testIsPrimeForLargePrimes() {
        assertTrue(IsPrime.isPrime(13441));
        assertTrue(IsPrime.isPrime(61));
    }
    @Test
    public void testIsPrimeForLargeComposites() {
        assertFalse(IsPrime.isPrime(255379));
        assertFalse(IsPrime.isPrime(77));
    }
    public void testNonPrimeNumberDueToModulusResult() { assertFalse(IsPrime.isPrime(9)); }
    public void testPrimeNumberAtLoopBoundary() { assertTrue(IsPrime.isPrime(2)); }
    public void testNonPrimeNumberDueToDivisorFound() { assertFalse(IsPrime.isPrime(4)); }
    public void testPrimeNumberWithinLoop() { assertFalse(IsPrime.isPrime(2147483646)); }
}