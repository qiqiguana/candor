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
    void testFilterByPrefix_EmptyList_ReturnsEmptyList() {
        List<Object> strings = new ArrayList<>();
        String prefix = "a";
        List<Object> result = FilterByPrefix.filterByPrefix(strings, prefix);
        assertTrue(result.isEmpty());
    }
    
    @Test
        public void testNothing(){
            FilterByPrefix s = new FilterByPrefix();
            }
    @Test
    public void testFilterByPrefix_with_null_string() {
        List<Object> strings = new ArrayList<>();
        strings.add(null);
        String prefix = "a";
        assertDoesNotThrow(() -> FilterByPrefix.filterByPrefix(strings, prefix));
    }
    @Test
    public void testFilterByPrefix_with_prefix_not_found() {
        List<Object> strings = new ArrayList<>();
        strings.add("abc");
        strings.add("bcd");
        strings.add("cde");
        String prefix = "e";
        assertEquals(new ArrayList<>(), FilterByPrefix.filterByPrefix(strings, prefix));
    }
    @Test
    public void testFilterByPrefix_with_multiple_matches() {
        List<Object> strings = new ArrayList<>();
        strings.add("xxx");
        strings.add("asd");
        strings.add("xxy");
        strings.add("john doe");
        strings.add("xxxAAA");
        strings.add("xxx");
        String prefix = "xxx";
        List<Object> expectedResult = new ArrayList<>();
        expectedResult.add("xxx");
        expectedResult.add("xxxAAA");
        expectedResult.add("xxx");
        assertEquals(expectedResult, FilterByPrefix.filterByPrefix(strings, prefix));
    }
                                    
}