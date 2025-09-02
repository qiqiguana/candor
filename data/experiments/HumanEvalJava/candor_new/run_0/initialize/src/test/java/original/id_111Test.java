package original;

import java.util.HashMap;
import java.util.Map;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
* Test class of Histogram.
*/
class HistogramTest {
    @Test
    void testHistogram_MultipleLettersWithSameMaxOccurrence_ReturnAll() {
        Map<String, Integer> expected = new HashMap<>();
        expected.put("a", 2);
        expected.put("b", 2);
        assertEquals(expected, Histogram.histogram("a b b a"));
    }
}