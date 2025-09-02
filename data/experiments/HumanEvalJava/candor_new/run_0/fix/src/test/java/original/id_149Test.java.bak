package original;

import java.util.Arrays;
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
void testSortedListSum_EmptyList_ReturnsEmptyList() {
  List<String> input = new ArrayList<>();
  List<Object> result = SortedListSum.sortedListSum(input);
  assertTrue(result.isEmpty());
}
@Test
public void testAllOddLengths() {
    SortedListSum s = new SortedListSum();
    List<String> input = Arrays.asList("a", "b", "c", "d");
    List<Object> result = s.sortedListSum(input);
    assertTrue(result.isEmpty());
}
@Test
public void testSortedListSum_with_even_length_strings() {
    List<String> input = new ArrayList<>(Arrays.asList("aa", "a", "aaa"));
    List<Object> expected = Arrays.asList((Object) "aa");
    assertEquals(expected, SortedListSum.sortedListSum(input));
}
@Test
public void testSortedListSum_with_multiple_even_length_strings() {
    List<String> input = new ArrayList<>(Arrays.asList("ab", "a", "aaa", "cd"));
    List<Object> expected = Arrays.asList((Object) "ab", (Object) "cd");
    assertEquals(expected, SortedListSum.sortedListSum(input));
}
}