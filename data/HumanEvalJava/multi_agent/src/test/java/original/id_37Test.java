package original;

import java.util.ArrayList;

import java.util.Collections;

import java.util.List;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
/**
* Test class of SortEven.
*/
class SortEvenTest {
    @Test
    void testSortEven_OddLengthList() {
        List<Integer> input = new ArrayList<>(List.of(1, 2, 3));
        List<Integer> expected = new ArrayList<>(List.of(1, 2, 3));
        assertEquals(expected, SortEven.sortEven(input));
    }
}