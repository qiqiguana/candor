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
    
    @Test
        public void testNothing(){
            SortedListSum s = new SortedListSum();
            }
    @Test
    public void testEmptyList() {
        List<String> input = new ArrayList<>();
        List<Object> expected = new ArrayList<>();
        assertEquals(expected, SortedListSum.sortedListSum(input));
    }
    @Test
    public void testSingleElementListEvenLength() {
        List<String> input = new ArrayList<>();
        input.add("aa");
        List<Object> expected = new ArrayList<>();
        expected.add("aa");
        assertEquals(expected, SortedListSum.sortedListSum(input));
    }
    @Test
    public void testSingleElementListOddLength() {
        List<String> input = new ArrayList<>();
        input.add("a");
        List<Object> expected = new ArrayList<>();
        assertEquals(expected, SortedListSum.sortedListSum(input));
    }
    @Test
    public void testEdgeCaseListWithDuplicates() {
        List<String> input = new ArrayList<>();
        input.add("aa");
        input.add("aa");
        input.add("ab");
        List<Object> expected = new ArrayList<>();
        expected.add("aa");
        expected.add("aa");
        expected.add("ab");
        assertEquals(expected, SortedListSum.sortedListSum(input));
    }
    @Test
    public void testEdgeCaseListWithOnlyEvenLengths() {
        List<String> input = new ArrayList<>();
        input.add("aa");
        input.add("bb");
        input.add("cc");
        List<Object> expected = new ArrayList<>();
        expected.add("aa");
        expected.add("bb");
        expected.add("cc");
        assertEquals(expected, SortedListSum.sortedListSum(input));
    }
    @Test
    public void testEdgeCaseListWithOnlyOddLengths() {
        List<String> input = new ArrayList<>();
        input.add("a");
        input.add("b");
        input.add("c");
        List<Object> expected = new ArrayList<>();
        assertEquals(expected, SortedListSum.sortedListSum(input));
    }
                                    
}