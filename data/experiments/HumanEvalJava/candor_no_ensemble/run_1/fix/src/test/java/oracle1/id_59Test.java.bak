package oracle1;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
/**
* Test class of LargestPrimeFactor.
*/
class LargestPrimeFactorTest {
    @Test
    void testLargestPrimeFactor() {
        int result = LargestPrimeFactor.largestPrimeFactor(2048);
        assertEquals(2, result);
    }
    
    @Test
        void testNothing(){
            LargestPrimeFactor s = new LargestPrimeFactor();
            }
    @Test
    public void testLargestPrimeFactor_PowerOfTwo() {
        int n = 2048;
        int expectedResult = 2;
        int result = LargestPrimeFactor.largestPrimeFactor(n);
        assertEquals(expectedResult, result);
    }
    @Test
    public void testLargestPrimeFactor_SmallCompositeNumber() {
        int n = 15;
        int expectedResult = 5;
        int result = LargestPrimeFactor.largestPrimeFactor(n);
        assertEquals(expectedResult, result);
    }
    @Test
    public void testLargestPrimeFactor_MediumCompositeNumber() {
        int n = 63;
        int expectedResult = 7;
        int result = LargestPrimeFactor.largestPrimeFactor(n);
        assertEquals(expectedResult, result);
    }
    @Test
    public void testLargestPrimeFactor_PrimeNumber() {
        int n = 23;
        int expectedResult = 23;
        int result = LargestPrimeFactor.largestPrimeFactor(n);
        assertEquals(expectedResult, result);
    }
    @Test
    public void testLargestPrimeFactor_LargeCompositeNumber() {
        int n = 13195;
        int expectedResult = 29;
        int result = LargestPrimeFactor.largestPrimeFactor(n);
        assertEquals(expectedResult, result);
    }
                                    
}