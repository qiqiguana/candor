package original;

import java.util.*;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
/**
* Test class of Common.
*/
class CommonTest {
    @Test
    void testCommon() {
        List<Integer> l1 = Arrays.asList(4, 3, 2, 8);
        List<Object> l2 = Arrays.asList(3, 2, 4);
        List<Object> expected = Arrays.asList(2, 3, 4);
        assertEquals(expected, Common.common(l1, l2));
    }
}