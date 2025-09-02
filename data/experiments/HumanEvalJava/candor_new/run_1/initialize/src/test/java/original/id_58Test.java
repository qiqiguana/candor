package original;

import java.util.*;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
/**
* Test class of Common.
*/
class CommonTest {
    @Test
    void testCommon_CompareTwoLists_ExpectSortedUniqueCommonElements() {
        List<Integer> list1 = new ArrayList<>(Arrays.asList(1, 4, 3, 34, 653, 2, 5));
        List<Object> list2 = new ArrayList<>(Arrays.asList(5, 7, 1, 5, 9, 653, 121));
        List<Object> expected = new ArrayList<>(Arrays.asList(1, 5, 653));
        assertEquals(expected, Common.common(list1, list2));
    }
}