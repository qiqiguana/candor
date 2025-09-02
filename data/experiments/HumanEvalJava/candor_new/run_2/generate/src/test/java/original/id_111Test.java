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
    
    @Test
        public void testNothing(){
            Histogram s = new Histogram();
            }
    @Test
    public void testNullInput() {
        Map<String, Integer> result = (Map<String, Integer>) Histogram.histogram(null);
        assertEquals(0, result.size());
    }
    @Test
    public void testEmptyStringInput() {
        Map<String, Integer> result = (Map<String, Integer>) Histogram.histogram("");
        assertEquals(0, result.size());
    }
    @Test
    public void testMultipleLettersWithDifferentFrequencies_1() {
        Map<String, Integer> result = (Map<String, Integer>) Histogram.histogram("a b a");
        assertEquals(1, result.size());
        assertEquals(2, result.get("a"));
    }
                                    
}