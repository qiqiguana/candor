package oracle1;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
/**
* Test class of IsPrime.
*/
class IsPrimeTest {
    @Test
    void isPrime_WhenNumberIsOne_ReturnsFalse() {
        // Arrange
        int n = 1;

        // Act
        boolean result = IsPrime.isPrime(n);

        // Assert
        assertFalse(result);
    }
    
    @Test
     void testNothing(){
         IsPrime s = new IsPrime();
         }
    @Test
    public void testPrimeNumber() {
    	Boolean result = IsPrime.isPrime(101);
    	assertTrue(result);
    }
    @Test
    public void testNonPrimeNumber() {
    	Boolean result = IsPrime.isPrime(6);
    	assertFalse(result);
    }
    @Test
    public void testEdgeCase_1() {
    	Boolean result = IsPrime.isPrime(1);
    	assertFalse(result);
    }
    @Test
    public void testEdgeCase_0() {
    	Boolean result = IsPrime.isPrime(0);
    	assertFalse(result);
    }
    @Test
    public void testNegativeNumber() {
    	Boolean result = IsPrime.isPrime(-5);
    	assertFalse(result);
    }
    @Test
    public void testLargePrimeNumber() {
        Boolean result = IsPrime.isPrime(255379);
        assertTrue(result);
    }
    @Test
    public void testNonIntegerInput2Fixed() {
        int n = 3;
        assertTrue(IsPrime.isPrime(n));
    }
                                  
}