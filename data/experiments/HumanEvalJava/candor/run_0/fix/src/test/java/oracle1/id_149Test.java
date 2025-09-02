package oracle1;

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
    void testSortedListSum_withOddAndEvenLengthStrings_returnsOnlyEvenLengthStringsSorted() {
        List<String> input = new ArrayList<>(List.of("aa", "a", "aaa"));
        List<Object> expected = new ArrayList<>(List.of("aa"));
        assertEquals(expected, SortedListSum.sortedListSum(input));
    }
    
    @Test
        public void testNothing(){
            SortedListSum s = new SortedListSum();
            }
    @Test
    public void TestEvenLengthStrings() {
        List<String> input = new ArrayList<>(List.of("aa", "ab", "cd"));
        List<Object> expected = new ArrayList<>(List.of("aa", "ab", "cd"));
        assertEquals(expected, SortedListSum.sortedListSum(input));
    }
    @Test
    public void TestOddLengthStrings() {
        List<String> input = new ArrayList<>(List.of("a", "aaa", "abcd"));
        List<Object> expected = new ArrayList<>(List.of("abcd"));
        assertEquals(expected, SortedListSum.sortedListSum(input));
    }
    @Test
    public void TestEmptyList() {
        List<String> input = new ArrayList<>();
        List<Object> expected = new ArrayList<>();
        assertEquals(expected, SortedListSum.sortedListSum(input));
    }
    @Test
    public void TestSingleElementList() {
        List<String> input = new ArrayList<>(List.of("aa"));
        List<Object> expected = new ArrayList<>(List.of("aa"));
        assertEquals(expected, SortedListSum.sortedListSum(input));
    }
    @Test
    public void TestLongStrings1() {
        List<String> input = new ArrayList<>(List.of("abcdefghijklmnopqrstuvwxyz", "0123456789"));
        List<Object> expected = new ArrayList<>(List.of("0123456789", "abcdefghijklmnopqrstuvwxyz"));
        assertEquals(expected, SortedListSum.sortedListSum(input));
    }
    @Test
    public void TestLongStrings2() {
        List<String> input = new ArrayList<>(List.of("abcdefghijklmnopqrstuvwxyz", "0123456789"));
        List<Object> expected = new ArrayList<>(List.of("0123456789", "abcdefghijklmnopqrstuvwxyz"));
        assertEquals(expected, SortedListSum.sortedListSum(input));
    }
                                    
}