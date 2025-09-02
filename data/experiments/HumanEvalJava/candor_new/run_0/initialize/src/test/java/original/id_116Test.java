package original;

import java.util.ArrayList;

import java.util.Collections;

import java.util.Comparator;

import java.util.List;


import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
/**
* Test class of SortArray.
*/
class SortArrayTest {
    @Test
    void testSortArray_1() {
        List<Integer> input = new ArrayList<>(List.of(5, 2, 8, 1, 9));
        List<Integer> expected = new ArrayList<>(List.of(1, 2, 5, 8, 9));
        Collections.sort(input);
        assertEquals(expected, input);
    }
}
