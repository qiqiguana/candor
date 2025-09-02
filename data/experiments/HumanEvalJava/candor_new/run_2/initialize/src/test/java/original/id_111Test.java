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
void testHistogramSingleLetter() {
        Map<String, Integer> expected = new HashMap<>();
        expected.put("a", 1);
        assertEquals(expected, Histogram.histogram("a"));
    }
}
