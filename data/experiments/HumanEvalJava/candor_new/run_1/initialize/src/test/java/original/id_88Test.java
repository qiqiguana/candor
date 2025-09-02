package original;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
/**
* Test class of SortArray1.
*/
class SortArray1Test {
    @Test
    void testSortArray_SumIsOdd_ReturnsSortedInAscendingOrder() {
        List<Object> array = new ArrayList<>();
        array.add(2);
        array.add(4);
        array.add(3);
        array.add(0);
        array.add(1);
        array.add(5);

        List<Object> expected = new ArrayList<>();
        expected.add(0);
        expected.add(1);
        expected.add(2);
        expected.add(3);
        expected.add(4);
        expected.add(5);

        assertEquals(expected, SortArray1.sortArray(array));
    }
}