package original;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
/**
* Test class of LargestDivisor.
*/
class LargestDivisorTest {
    @Test
    void testLargestDivisor_Returns5_WhenInputIs15() {
        int result = LargestDivisor.largestDivisor(15);
        assertEquals(5, result);
    }
    
    @Test
        public void testNothing(){
            LargestDivisor s = new LargestDivisor();
            }
    @Test
    public void testLargestDivisorPrime() {
        int result = LargestDivisor.largestDivisor(7);
        assertEquals(1, result);
    }
    @Test
    public void testLargestDivisorPerfectSquare() {
        int result = LargestDivisor.largestDivisor(49);
        assertEquals(7, result);
    }
    @Test
    public void testLargestDivisorEven() {
        int result = LargestDivisor.largestDivisor(10);
        assertEquals(5, result);
    }
    @Test
    public void testLargestDivisorOdd() {
        int result = LargestDivisor.largestDivisor(15);
        assertEquals(5, result);
    }
    @Test
    public void testLargestDivisorEdgeCase() {
        int result = LargestDivisor.largestDivisor(1);
        assertEquals(1, result);
    }
                                    
}