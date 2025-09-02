package original;

import java.util.Arrays;
import java.util.List;
import java.util.ArrayList;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
/**
* Test class of CountNums.
*/
class CountNumsTest {
    @Test
    void testCountNums_EmptyList_ReturnsZero() {
        List<Object> arr = new ArrayList<>();
        int result = CountNums.countNums(arr);
        assertEquals(0, result);
    }
    
    @Test
        public void testNothing(){
            CountNums s = new CountNums();
            }
    @Test
    public void testEmptyArray() {
        List<Object> input = new ArrayList<>();
        int expected = 0;
        assertEquals(expected, CountNums.countNums(input));
    }
    @Test
    public void testSinglePositiveNumber() {
        List<Object> input = Arrays.asList(5);
        int expected = 1;
        assertEquals(expected, CountNums.countNums(input));
    }
    @Test
    public void testSingleNegativeNumber() {
        List<Object> input = Arrays.asList(-5);
        int expected = 0;
        assertEquals(expected, CountNums.countNums(input));
    }
    @Test
    public void testMultiplePositiveNumbers() {
        List<Object> input = Arrays.asList(1, 2, 3, 4, 5);
        int expected = 5;
        assertEquals(expected, CountNums.countNums(input));
    }
    @Test
    public void testMultipleNegativeNumbersCorrected() {
        List<Object> input = Arrays.asList(-1, -2, -3, -4, -5);
        int expected = 0;
        assertEquals(expected, CountNums.countNums(input));
    }
    @Test
    public void testLargeNumbersFixed() {
        List<Object> input = Arrays.asList(123456789, -987654321);
        int expected = 2;
        assertEquals(expected, CountNums.countNums(input));
    }
                                    
}