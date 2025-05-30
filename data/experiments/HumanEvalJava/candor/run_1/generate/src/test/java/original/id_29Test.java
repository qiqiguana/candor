package original;

import java.util.ArrayList;

import java.util.List;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
/**
* Test class of FilterByPrefix.
*/
class FilterByPrefixTest {

@Test
void testFilterByPrefixWithMatchingStrings() {
    List<Object> input = new ArrayList<>();
    input.add("abc");
    input.add("bcd");
    input.add("cde");
    input.add("array");
    String prefix = "a";
    List<Object> expected = new ArrayList<>();
    expected.add("abc");
    expected.add("array");
    assertEquals(expected, FilterByPrefix.filterByPrefix(input, prefix));
}

@Test
    public void testNothing(){
        FilterByPrefix s = new FilterByPrefix();
        }
@Test
public void testEmptyList() {
    List<Object> input = new ArrayList<>();
    List<Object> result = FilterByPrefix.filterByPrefix(input, "a");
    assertTrue(result.isEmpty());
}
@Test
public void testNullList() {
    assertThrows(NullPointerException.class, () -> FilterByPrefix.filterByPrefix(null, "a"));
}
@Test
public void testSingleMatch() {
    List<Object> input = java.util.Arrays.asList("abc");
    List<Object> result = FilterByPrefix.filterByPrefix(input, "a");
    assertEquals(1, result.size());
    assertEquals("abc", result.get(0));
}
                                

}