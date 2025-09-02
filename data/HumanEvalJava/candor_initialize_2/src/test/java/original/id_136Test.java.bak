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
    void testLargestSmallestIntegers() {
        List<Object> input = new ArrayList<>();
        input.add(-1);
        input.add(0);
        input.add(1);
        List<Integer> result = LargestSmallestIntegers.largestSmallestIntegers(input);
        assertNotNull(result);
        assertEquals(2, result.size());
        assertNotNull(result.get(0));
        assertNotNull(result.get(1));
        assertEquals(-1, (int)result.get(0));
        assertEquals(1, (int)result.get(1));
    }
    @Test
    public void testEmptyList() {
        List<Object> input = new ArrayList<>();
        Object[] expected = {null, null};
        assertArrayEquals(expected, LargestSmallestIntegers.largestSmallestIntegers(input).toArray());
    }
}