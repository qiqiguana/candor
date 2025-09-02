package original;

import java.util.*;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
/**
* Test class of Maximum1.
*/
class Maximum1Test {
    @Test
    public void testMaximum_WithArrayAndK_ReturnsCorrectList() {
        List<Integer> arr = Arrays.asList(4, -4, 4);
        int k = 2;
        List<Object> expected = Arrays.asList(4, 4);
        List<Object> actual = Maximum1.maximum(arr, k);
        assertEquals(expected, actual);
    }
}