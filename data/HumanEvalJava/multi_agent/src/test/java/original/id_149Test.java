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
    void testSortedListSum() {
        List<String> lst = new ArrayList<>();
        lst.add("aa");
        lst.add("a");
        lst.add("aaa");
        List<Object> expected = new ArrayList<>();
        expected.add("aa");
        assertEquals(expected, SortedListSum.sortedListSum(lst));
    }
}