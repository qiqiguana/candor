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
    
    @Test
        public void testNothing(){
            IsMultiplyPrime s = new IsMultiplyPrime();
            }
    @Test
    public void testIsMultiplyPrime_MultipleOfThreePrimes() {
        boolean result = IsMultiplyPrime.isMultiplyPrime(30);
        assertTrue(result);
    }
    @Test
    public void testIsMultiplyPrime_SinglePrimeFactor() {
        boolean result = IsMultiplyPrime.isMultiplyPrime(5);
        assertFalse(result);
    }
    @Test
    public void testIsMultiplyPrime_MultipleOfTwoPrimes() {
        boolean result = IsMultiplyPrime.isMultiplyPrime(10);
        assertFalse(result);
    }
    @Test
    public void testIsMultiplyPrime_ProductOfRepeatedPrimeFactor() {
        boolean result = IsMultiplyPrime.isMultiplyPrime(125);
        assertTrue(result);
    }
    @Test
    public void testIsMultiplyPrime_GreaterThan100() {
        boolean result = IsMultiplyPrime.isMultiplyPrime(101);
        assertFalse(result);
    }
                                    
}