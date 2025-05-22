package original;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
* Test class of Median.
*/
class MedianTest {
    @Test
    void testMedian_EvenNumberOfElements_ReturnsCorrectMedian() {
        List<Integer> numbers = new ArrayList<>(List.of(1, 3, 5, 7));
        assertEquals(4.0, Median.median(numbers), "The median of [1, 3, 5, 7] should be 4.0");
    }
}