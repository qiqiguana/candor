package original;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
/**
* Test class of IsPrime.
*/
class IsPrimeTest {
    @Test
    void isPrime_Negative_False() {
        // Arrange
        int n = -1;
        boolean expected = false;
        
        // Act
        boolean actual = IsPrime.isPrime(n);
        
        // Assert
        assertEquals(expected, actual);
    }
    @Test
    public void testIsPrimeWithPositiveNumber() {
        Boolean result = IsPrime.isPrime(7);
        assertTrue(result);
    }
    @Test
    public void testIsPrimeWithNegativeNumber() {
        Boolean result = IsPrime.isPrime(4);
        assertFalse(result);
    }
    @Test
    public void testIsPrimeWith1() {
        Boolean result = IsPrime.isPrime(1);
        assertFalse(result);
    }
    @Test
    public void testIsPrimeWith0() {
        Boolean result = IsPrime.isPrime(0);
        assertFalse(result);
    }
    @Test
    public void testIsPrimeWithLargeCompositeNumber() {
        Boolean result = IsPrime.isPrime(255379);
        assertFalse(result);
    }
    @Test
    public void testIsPrimeWithLargeNonPrimeNumber() {
        Boolean result = IsPrime.isPrime(255378);
        assertFalse(result);
    }
    @Test
    public void testPrimeNumber() {
    	Boolean result = IsPrime.isPrime(101);
    	assertTrue(result);
    }
    @Test
    public void testCompositeNumber() {
    	Boolean result = IsPrime.isPrime(6);
    	assertFalse(result);
    }
    @Test
    public void testEdgeCase1() {
    	Boolean result = IsPrime.isPrime(1);
    	assertFalse(result);
    }
    @Test
    public void testEdgeCaseLessThan1() {
    	Boolean result = IsPrime.isPrime(-5);
    	assertFalse(result);
    }
    @Test
    public void testLargePrimeNumber() {
    	Boolean result = IsPrime.isPrime(255379);
    	assertFalse(result);
    }
    @Test
    public void testSquareOfPrimeNumber() {
    	Boolean result = IsPrime.isPrime(121);
    	assertFalse(result);
    }
    @Test public void testIsPrime_NotPrime_LessThanEqualOne_Fixed() { assertTrue(IsPrime.isPrime(0) == false); }
}