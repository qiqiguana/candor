package original;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
/**
* Test class of IsMultiplyPrime.
*/
class IsMultiplyPrimeTest {
    @Test
    void isMultiplyPrime_WhenGiven30_ReturnsTrue() {
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
    public void testIsMultiplyPrime_LessThanThreePrimes_ReturnFalse() {
        int a = 10;
        Boolean result = IsMultiplyPrime.isMultiplyPrime(a);
        assertFalse(result);
    }
                                    
}