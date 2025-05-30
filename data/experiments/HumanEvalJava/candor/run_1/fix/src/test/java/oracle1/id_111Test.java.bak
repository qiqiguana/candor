package oracle1;

import java.util.HashMap;
import java.util.Map;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
* Test class of Histogram.
*/
class HistogramTest {
    @Test
    void testHistogram_ReturnsEmptyMap_WhenInputIsNull() {
        Map<String, Integer> result = (Map<String, Integer>) Histogram.histogram(null);
        assertTrue(result.isEmpty());
    }
    
    @Test
     void testNothing(){
         Histogram s = new Histogram();
         }
    @Test
    public void test_empty_string() {
        Object result = Histogram.histogram("");
        assertEquals(new HashMap<>(), result);
    }
    @Test
    public void test_single_character() {
        Object result = Histogram.histogram("a");
        Map<String, Integer> expected = new HashMap<>();
        expected.put("a", 1);
        assertEquals(expected, result);
    }
    @Test
    public void test_multiple_characters() {
        Object result = Histogram.histogram("a b c");
        Map<String, Integer> expected = new HashMap<>();
        expected.put("a", 1);
        expected.put("b", 1);
        expected.put("c", 1);
        assertEquals(expected, result);
    }
    @Test
    public void test_duplicate_characters() {
        Object result = Histogram.histogram("a b b a");
        Map<String, Integer> expected = new HashMap<>();
        expected.put("a", 2);
        expected.put("b", 2);
        assertEquals(expected, result);
    }
    @Test
    public void test_null_input() {
        Object result = Histogram.histogram(null);
        assertEquals(new HashMap<>(), result);
    }
    @Test
    public void test_single_space_input() {
        Object result = Histogram.histogram(" ");
        assertEquals(new HashMap<>(), result);
    }
                                  
}