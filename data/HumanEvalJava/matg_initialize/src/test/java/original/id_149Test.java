package original;

import java.util.ArrayList;

import java.util.Collections;

import java.util.Comparator;

import java.util.List;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
/**
* Test class of SortedListSum.
*/
class SortedListSumTest {
    @Test
    void testSortedListSum_withStringsOfEvenLengths_shouldReturnSortedAndFilteredArray() {
        List<String> input = new ArrayList<>(List.of("aa", "a", "aaa"));
        List<Object> expected = new ArrayList<>(List.of("aa"));
        assertEquals(expected, SortedListSum.sortedListSum(input));
    }
}