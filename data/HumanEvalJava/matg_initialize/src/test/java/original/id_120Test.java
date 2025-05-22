package original;

import java.util.*;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
/**
* Test class of Maximum1.
*/
class Maximum1Test {
    @Test
    void testMaximum() {
        List<Integer> arr = new ArrayList<>(Arrays.asList(4, -4, 4));
        int k = 2;
        Object[] expected = {4, 4};
        Object[] actual = Maximum1.maximum(arr, k).toArray();
        assertArrayEquals(expected, actual);
    }
}
