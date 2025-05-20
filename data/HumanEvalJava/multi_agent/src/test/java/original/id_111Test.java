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
    void testHistogram_WhenInputStringIsEmpty_ReturnEmptyMap() {
        Map<String, Integer> expected = new HashMap<>();
        Object actual = Histogram.histogram("");
        assertEquals(expected, actual);
    }
}
