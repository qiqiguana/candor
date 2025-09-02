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
        List<Object> list = new ArrayList<>();
        list.add(-10);
        list.add(5);
        list.add(-20);
        list.add(0);
        List<Integer> result = LargestSmallestIntegers.largestSmallestIntegers(list);
        assertNotNull(result);
        assertEquals(2, result.size());
    }
}