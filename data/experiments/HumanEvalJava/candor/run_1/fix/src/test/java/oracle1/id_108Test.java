package oracle1;

import java.util.Arrays;
import static org.junit.jupiter.api.Assertions.assertEquals;
import java.util.List;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
* Test class of CountNums.
*/
class CountNumsTest {
    @Test
    void testCountNumsWithPositiveNumbers() {
        List<Object> arr = List.of(1, 2, 3);
        assertEquals(3, CountNums.countNums(arr));
    }
    
    @Test
        void testNothing(){
            CountNums s = new CountNums();
            }
    @Test
    public void testCountNumsWithMultiplePositiveNumbers1() {
    	java.util.List<java.lang.Object> input = java.util.Arrays.asList(1, 2, 3);
    	int expected = 3;
    	org.junit.jupiter.api.Assertions.assertEquals(expected, oracle1.CountNums.countNums(input));
    }
    @Test
    public void testCountNumsWithMultiplePositiveNumbers() {
        java.util.List<java.lang.Object> input = java.util.Arrays.asList(1, 2, 3);
        int expected = 3;
        org.junit.jupiter.api.Assertions.assertEquals(expected, oracle1.CountNums.countNums(input));
    }
    @Test
    public void testCountNumsWithNumberWithSumOfDigitsGreaterThanZero_1() {
    	java.util.List<java.lang.Object> input = java.util.Arrays.asList(10);
    	int expected = 1;
    	org.junit.jupiter.api.Assertions.assertEquals(expected, oracle1.CountNums.countNums(input));
    }
    @Test
    public void testCountNumsWithNumberWithSumOfDigitsGreaterThanZero() {
        List<Object> input = Arrays.asList((Integer) 12);
        int expected = 1;
        assertEquals(expected, CountNums.countNums(input));
    }
    @Test
    public void testCountNumsWithMultipleNumbers1() {
        List<Object> input = Arrays.asList(12, 34);
        int expected = 2;
        assertEquals(expected, CountNums.countNums(input));
    }
    @Test
    public void testCountNumsWithMixOfPositiveAndNegativeNumbersFixed() {
        List<Object> input = Arrays.asList(12, -34);
        int expected = 2;
        assertEquals(expected, CountNums.countNums(input));
    }
                                    
}