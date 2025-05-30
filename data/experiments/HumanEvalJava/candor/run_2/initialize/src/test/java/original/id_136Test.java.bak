package original;

import java.util.ArrayList;

import java.util.List;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
/**
* Test class of LargestSmallestIntegers.
*/
class LargestSmallestIntegersTest {
    @Test
    void testLargestSmallestIntegers_EmptyList_ReturnsNullValues() {
        List<Object> input = new ArrayList<>();
        List<Integer> result = LargestSmallestIntegers.largestSmallestIntegers(input);
        assertEquals(2, result.size());
        assertNull(result.get(0));
        assertNull(result.get(1));
    }
    @Test
    public void testNullInput() {
        List<Object> input = null;
        assertThrows(NullPointerException.class, () -> LargestSmallestIntegers.largestSmallestIntegers(input));
    }
    @Test
    public void testEmptyList() {
        List<Object> input = new ArrayList<>();
        assertEquals(2, LargestSmallestIntegers.largestSmallestIntegers(input).size());
        assertNull(LargestSmallestIntegers.largestSmallestIntegers(input).get(0));
        assertNull(LargestSmallestIntegers.largestSmallestIntegers(input).get(1));
    }
    @Test
    public void testLargestSmallestIntegers_with_empty_list() {
        List<Object> lst = new ArrayList<>();
        List<Integer> result = LargestSmallestIntegers.largestSmallestIntegers(lst);
        assertEquals(null, result.get(0));
        assertEquals(null, result.get(1));
    }
}