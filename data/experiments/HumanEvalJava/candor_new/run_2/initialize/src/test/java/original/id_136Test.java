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
        List<Integer> expected = new ArrayList<>();
        expected.add(null);
        expected.add(null);
        assertEquals(expected, LargestSmallestIntegers.largestSmallestIntegers(input));
    }
}