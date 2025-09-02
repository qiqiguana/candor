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
    void testHistogram() {
        Map<String, Integer> expected = new HashMap<>();
        expected.put("a", 2);
        expected.put("b", 2);
        assertEquals(expected, Histogram.histogram("a b b a"));
    }
    
    @Test
        public void testNothing(){
            Histogram s = new Histogram();
            }
    @Test
    void testNullInputHandling() {
        Map<String, Integer> expected = new HashMap<>();
        Object result = Histogram.histogram(null);
        assertEquals(expected, result);
    }
    @Test
    public void testEmptyString() {
        Map<String, Integer> result = (Map<String, Integer>) Histogram.histogram("");
        assertTrue(result.isEmpty());
    }
    @Test
    public void MultipleLettersWithSameMaxFrequency() {
        Map<String, Integer> expectedResult = new HashMap<>();
        expectedResult.put("a", 2);
        expectedResult.put("c", 2);
        assertEquals(expectedResult, Histogram.histogram("a b a c c"));
    }
                                    
}