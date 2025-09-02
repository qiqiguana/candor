package original;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
/**
* Test class of LargestDivisor.
*/
class LargestDivisorTest {

    @Test
    void testLargestDivisor_10_Returns5() {
        int expected = 5;
        int actual = LargestDivisor.largestDivisor(10);
        assertEquals(expected, actual);
    }
    
    @Test
        public void testNothing(){
            LargestDivisor s = new LargestDivisor();
            }
    @Test
    public void TestLargestDivisorWithPrimeNumber() {
        int result = LargestDivisor.largestDivisor(Integer.valueOf(23));
        assertEquals(Integer.valueOf(1), Integer.valueOf(result));
    }
    @Test
    public void TestLargestDivisorWithPerfectSquare() {
        int result = LargestDivisor.largestDivisor(Integer.valueOf(25));
        assertEquals(Integer.valueOf(5), Integer.valueOf(result));
    }
    @Test
    public void testLargestDivisorPrime() {
        int result = LargestDivisor.largestDivisor(23);
        assertEquals(1, result);
    }
    @Test
    public void testLargestDivisorPerfectSquare() {
        int result = LargestDivisor.largestDivisor(25);
        assertEquals(5, result);
    }
    @Test
    void testLargestDivisorWithPrimeNumber() {
        assertEquals(1, LargestDivisor.largestDivisor(7));
    }
    @Test
    void testLargestDivisorWithPerfectSquare() {
        assertEquals(7, LargestDivisor.largestDivisor(49));
    }
    @Test
    void testLargestDivisorWithLargeNumber() {
        assertEquals(500, LargestDivisor.largestDivisor(1000));
    }
                                    
}