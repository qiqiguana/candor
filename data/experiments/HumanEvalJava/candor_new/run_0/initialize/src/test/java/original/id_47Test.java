package original;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;
/**
* Test class of Median.
*/
class MedianTest {
    @Test
    void testMedian_EvenSizeList_ReturnAverageOfTwoMiddleElements() {
        List<Integer> numbers = new ArrayList<>();
        numbers.add(1);
        numbers.add(2);
        numbers.add(3);
        numbers.add(4);
        double expected = 2.5;
        assertEquals(expected, Median.median(numbers));
    }
}
