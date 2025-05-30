package oracle1;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
/**
* Test class of LargestDivisor.
*/
class LargestDivisorTest {
    @Test
    void largestDivisor_should_return_largest_divisor_of_a_number() {
        assertEquals(5, LargestDivisor.largestDivisor(15));
    }
    
    @Test
        void testNothing(){
            LargestDivisor s = new LargestDivisor();
            }
    @Test
    public void testLargestDivisor_Positive_15() {
        int input = 15;
        int expectedResult = 5;
        int actualResult = LargestDivisor.largestDivisor(input);
        assertEquals(expectedResult, actualResult);
    }
    @Test
    public void testLargestDivisor_Negative_InvalidInputs() {
        int[] inputs = {0, -10};
        for (int input : inputs) {
            assertThrows(IllegalArgumentException.class, () -> LargestDivisor.largestDivisor(input));
        }
    }
    @Test
    public void testLargestDivisor_EdgeCase_2() {
        int input = 2;
        int expectedResult = 1;
        int actualResult = LargestDivisor.largestDivisor(input);
        assertEquals(expectedResult, actualResult);
    }
    @Test
    public void testLargestDivisor_SquareNumbers_Fixed() {
        int input = 64;
        int expectedResult = 32;
        int actualResult = LargestDivisor.largestDivisor(input);
        assertEquals(expectedResult, actualResult);
    }
                                    
}