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
    void testLargestSmallestIntegers_withOnlyPositiveNumbers() {
        List<Object> input = new ArrayList<>();
        input.add(2);
        input.add(4);
        input.add(1);
        input.add(3);
        input.add(5);
        input.add(7);

        List<Integer> expected = new ArrayList<>();
        expected.add(null);
        expected.add(1);

        assertEquals(expected, LargestSmallestIntegers.largestSmallestIntegers(input));
    }
}
