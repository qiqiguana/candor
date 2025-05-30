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
    void test_histogram_should_return_expected_result_for_valid_input() {
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
    public void testEmptyString() {
        Map<String, Integer> result = (Map<String, Integer>) Histogram.histogram(" ");
        assertTrue(result.isEmpty());
    }
    @Test
    public void testSingleCharacter() {
        Map<String, Integer> result = (Map<String, Integer>) Histogram.histogram("a");
        assertEquals(1, (int) result.get("a"));
    }
    @Test
    public void testMultipleCharacters() {
        Map<String, Integer> result = (Map<String, Integer>) Histogram.histogram("a b c d e");
        assertEquals(1, (int) result.get("a"));
        assertEquals(1, (int) result.get("b"));
        assertEquals(1, (int) result.get("c"));
        assertEquals(1, (int) result.get("d"));
        assertEquals(1, (int) result.get("e"));
    }
    @Test
    public void testRepeatingCharactersFixed() {
        Map<String, Integer> result = (Map<String, Integer>) Histogram.histogram("a b a c d e");
        assertEquals(2, (int) result.get("a"));
    }
    @Test
    public void testNullInputFixed() {
        Map<String, Integer> result = (Map<String, Integer>) Histogram.histogram(null);
        assertTrue(result.isEmpty());
    }
    @Test
    public void testSingleRepeatingCharacterFixed() {
        Map<String, Integer> result = (Map<String, Integer>) Histogram.histogram("a a");
        assertEquals(2, (int) result.get("a"));
    }
    @Test
    public void testMultipleOccurrences() {
        Map<String, Integer> result = (Map<String, Integer>) Histogram.histogram("a a");
        assertEquals("{a=2}", result.toString());
    }
    @Test
    public void testAlphabet() {
        Map<String, Integer> result = (Map<String, Integer>) Histogram.histogram("a b c d e f g h i j k l m n o p q r s t u v w x y z");
        assertEquals("{a=1, b=1, c=1, d=1, e=1, f=1, g=1, h=1, i=1, j=1, k=1, l=1, m=1, n=1, o=1, p=1, q=1, r=1, s=1, t=1, u=1, v=1, w=1, x=1, y=1, z=1}", result.toString());
    }
    @Test
    public void testEmptyStringHistogram() {
        Map<String, Integer> result = (Map<String, Integer>) Histogram.histogram(" ");
        assertEquals(0, result.size());
    }
    @Test
    public void testSingleCharacterHistogram() {
        Map<String, Integer> result = (Map<String, Integer>) Histogram.histogram("a");
        assertEquals(1, (int)result.get("a"));
    }
    @Test
    public void testMultipleCharactersUnique() {
        Map<String, Integer> result = (Map<String, Integer>) Histogram.histogram("a b");
        assertEquals(2, result.size());
    }
    @Test
    public void testHistogramEmptyString() {
    	Map<String, Integer> expected = new HashMap<>();
    	Object actual = Histogram.histogram("");
    	assertEquals(expected, actual);
    }
    @Test
    public void testHistogramSingleLetter() {
    	Map<String, Integer> expected = new HashMap<>();
    	expected.put("a", 1);
    	Object actual = Histogram.histogram("a");
    	assertEquals(expected, actual);
    }
    @Test
    public void testHistogramMultipleLetters() {
    	Map<String, Integer> expected = new HashMap<>();
    	expected.put("a", 1);
    	expected.put("b", 1);
    	expected.put("c", 1);
    	Object actual = Histogram.histogram("a b c");
    	assertEquals(expected, actual);
    }
    @Test
    public void testHistogramDuplicateLetters() {
    	Map<String, Integer> expected = new HashMap<>();
    	expected.put("a", 2);
    	expected.put("b", 2);
    	Object actual = Histogram.histogram("a b a b");
    	assertEquals(expected, actual);
    }
    @Test
    public void testHistogramDuplicateLetters1() {
    	Map<String, Integer> expected = new HashMap<>();
    	expected.put("a", 2);
    	expected.put("b", 2);
    	Object actual = Histogram.histogram("b a b a");
    	assertEquals(expected, actual);
    }
    @Test
    public void testHistogram_SingleCharacter() {
        Map<String, Integer> expected = new HashMap<>();
        expected.put("a", 1);
        assertEquals(expected, Histogram.histogram("a"));
    }
    @Test
    public void testHistogram_MultipleCharacters() {
        Map<String, Integer> expected = new HashMap<>();
        expected.put("a", 1);
        expected.put("b", 1);
        expected.put("c", 1);
        assertEquals(expected, Histogram.histogram("a b c"));
    }
    @Test
    public void testHistogram_RepeatedCharacters() {
        Map<String, Integer> expected = new HashMap<>();
        expected.put("a", 2);
        expected.put("b", 2);
        assertEquals(expected, Histogram.histogram("a b b a"));
    }
    @Test
    public void testHistogram_EmptyInput() {
        Map<String, Integer> expected = new HashMap<>();
        assertEquals(expected, Histogram.histogram(""));
    }
    @Test
    public void testHistogram_NullInput() {
        Map<String, Integer> expected = new HashMap<>();
        assertEquals(expected, Histogram.histogram(null));
    }
    @Test
    public void testHistogram_SingleSpaceInput() {
        Map<String, Integer> expected = new HashMap<>();
        assertEquals(expected, Histogram.histogram(" "));
    }
    @Test
    public void testHistogram_MultipleSpacesInput() {
        Map<String, Integer> expected = new HashMap<>();
        assertEquals(expected, Histogram.histogram("   "));
    }
                                    
}