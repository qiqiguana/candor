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
    void testLargestSmallestIntegers_EmptyList_ReturnsNull() {
        List<Object> input = new ArrayList<>();
        List<Integer> result = LargestSmallestIntegers.largestSmallestIntegers(input);
        assertNotNull(result);
        assertEquals(2, result.size());
        assertNull(result.get(0));
        assertNull(result.get(1));
    }

}