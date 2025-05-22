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
    
    @Test
        public void testNothing(){
            IsPrime s = new IsPrime();
            }
    @Test
    public void testIsPrime_PrimeNumber_ReturnTrue() {
        boolean result = IsPrime.isPrime(101);
        assertTrue(result);
    }
    @Test
    public void testIsPrime_NonPrimeNumber_ReturnFalse() {
        boolean result = IsPrime.isPrime(6);
        assertFalse(result);
    }
    @Test
    public void testIsPrime_EdgeCaseOne_ReturnFalse() {
        boolean result = IsPrime.isPrime(1);
        assertFalse(result);
    }
    @Test
    public void testIsPrime_EdgeCaseNegativeNumber_ReturnFalse() {
        boolean result = IsPrime.isPrime(-5);
        assertFalse(result);
    }
    @Test
    public void testIsPrime_LargePrimeNumber_ReturnFalse() {
        boolean result = IsPrime.isPrime(255379);
        assertFalse(result);
    }
    @Test
    public void testIsPrime_Zero_ReturnFalse() {
        boolean result = IsPrime.isPrime(0);
        assertFalse(result);
    }
                                    
}