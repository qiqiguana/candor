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
    void testHistogram_SingleLetter_ReturnMapWithCount() {
        Map<String, Integer> expected = new HashMap<>();
        expected.put("a", 1);
        assertEquals(expected, Histogram.histogram("a"));
    }
    @Test
    public void testEmptyString() {
        Map<String, Integer> expected = new HashMap<>();
        Object result = Histogram.histogram("");
        assertEquals(expected, result);
    }
    @Test
    public void testSingleLetter() {
        Map<String, Integer> expected = new HashMap<>();
        expected.put("a", 1);
        Object result = Histogram.histogram("a");
        assertEquals(expected, result);
    }
    @Test
    public void testMultipleLettersWithSameFrequency() {
        Map<String, Integer> expected = new HashMap<>();
        expected.put("a", 2);
        expected.put("b", 2);
        Object result = Histogram.histogram("a b c a b");
        assertEquals(expected, result);
    }
    @Test
    public void testMultipleLettersWithDifferentFrequencies() {
        Map<String, Integer> expected = new HashMap<>();
        expected.put("b", 4);
        Object result = Histogram.histogram("b b b b a");
        assertEquals(expected, result);
    }
    @Test
    public void testNullInput() {
        Map<String, Integer> expected = new HashMap<>();
        Object result = Histogram.histogram(null);
        assertEquals(expected, result);
    }
    @Test
    public void TestHistogramEmptyInput() {
        Map<String, Integer> result = (Map<String, Integer>) Histogram.histogram("");
        assertTrue(result.isEmpty());
    }
    @Test
    public void TestHistogramSingleLetter() {
        Map<String, Integer> result = (Map<String, Integer>) Histogram.histogram("a");
        assertEquals(1, (int) result.get("a"));
    }
    @Test
    public void TestHistogramMultipleLetters() {
        Map<String, Integer> result = (Map<String, Integer>) Histogram.histogram("a b c");
        assertEquals(1, (int) result.get("a"));
        assertEquals(1, (int) result.get("b"));
        assertEquals(1, (int) result.get("c"));
    }
    @Test
    public void TestHistogramLetterRepetition2() {
        Map<String, Integer> result = (Map<String, Integer>) Histogram.histogram("a b b c");
        assertEquals(1, result.size());
        assertEquals(2, (int) result.get("b"));
    }
    @Test
    public void TestHistogramEdge() {
        Map<String, Integer> result = (Map<String, Integer>) Histogram.histogram("a b c d e f g h i j k l m n o p q r s t u v w x y z a");
        assertEquals(2, (int) result.get("a"));
    }
    @Test
    public void TestHistogramNullInput() {
        Map<String, Integer> result = (Map<String, Integer>) Histogram.histogram(null);
        assertTrue(result.isEmpty());
    }
    @Test
    public void TestHistogramOnlySpaces() {
        Map<String, Integer> result = (Map<String, Integer>) Histogram.histogram("   ");
        assertTrue(result.isEmpty());
    }
    @Test
    public void TestHistogramSingleSpace() {
        Map<String, Integer> result = (Map<String, Integer>) Histogram.histogram(" ");
        assertTrue(result.isEmpty());
    }
}