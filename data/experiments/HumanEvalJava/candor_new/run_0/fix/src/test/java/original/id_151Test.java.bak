package original;

import java.util.Arrays;
import java.util.List;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
* Test class of DoubleTheDifference.
*/
class DoubleTheDifferenceTest {
    @Test
    void testDoubleTheDifference_OddNumbers_ReturnSumOfSquares() {
        List<Object> numbers = List.of(1, 3, 5);
        int result = DoubleTheDifference.doubleTheDifference(numbers);
        assertEquals(35, result);
    }
    
    @Test
        public void testNothing(){
            DoubleTheDifference s = new DoubleTheDifference();
            }
    @Test
    void testDoubleTheDifference_with_non_integer_values_corrected_1() {
        List<Object> lst = Arrays.asList(1, 2.5, 3);
        assertEquals(10, DoubleTheDifference.doubleTheDifference(lst));
    }
    @Test
    void testDoubleTheDifference_with_non_integer_values_corrected_2() {
        List<Object> lst = Arrays.asList(1, "a", 3);
        assertEquals(10, DoubleTheDifference.doubleTheDifference(lst));
    }
    @Test
    void testDoubleTheDifference_with_negative_numbers() {
        List<Object> lst = Arrays.asList(-1, -2, 3);
        assertEquals(9, DoubleTheDifference.doubleTheDifference(lst));
    }
                                    
}