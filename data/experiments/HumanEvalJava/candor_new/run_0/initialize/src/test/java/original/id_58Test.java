package original;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
/**
* Test class of Common.
*/
class CommonTest {
    @Test
    void testCommon_TwoLists_HasCommonElements_1() {
        List<Integer> l1 = new ArrayList<>(Arrays.asList(4, 3, 2, 8));
        List<Object> l2 = new ArrayList<>(Arrays.asList(3, 2, 4));
        List<Object> expected = new ArrayList<>(Arrays.asList(2, 3, 4));
        assertEquals(expected, Common.common(l1, l2));
    }
}