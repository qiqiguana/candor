package original;

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
}