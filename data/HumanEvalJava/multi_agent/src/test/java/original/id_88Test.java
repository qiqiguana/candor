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
    void testSortArray_OddSum_AscendingOrder() {
        List<Object> array = new ArrayList<>();
        array.add(2);
        array.add(4);
        array.add(3);
        array.add(0);
        array.add(1);
        array.add(5);
        List<Object> expectedArray = new ArrayList<>();
        expectedArray.add(0);
        expectedArray.add(1);
        expectedArray.add(2);
        expectedArray.add(3);
        expectedArray.add(4);
        expectedArray.add(5);
        assertEquals(expectedArray, SortArray1.sortArray(array));
    }
}
