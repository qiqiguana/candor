package original;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
/**
* Test class of IsPrime.
*/
class IsPrimeTest {
    @Test
    void testIsPrime_WhenInputNumberIsLessThanEqualOne_ReturnsFalse() {
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
    public void testIsPrime_LargerPrimeNumbers() {
        assertTrue(IsPrime.isPrime(997));
    }
    @Test
    public void TestIsPrimeWithNonPrimeNumberThatHasMultipleFactors() {
        int n = 6;
        Boolean result = IsPrime.isPrime(n);
        assertFalse(result);
    }
                                    
}