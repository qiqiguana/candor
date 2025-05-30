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
    void testFilterByPrefix_1() {
        List<Object> strings = new ArrayList<>();
        strings.add("abc");
        strings.add("bcd");
        strings.add("cde");
        strings.add("array");
        String prefix = "a";
        List<Object> expected = new ArrayList<>();
        expected.add("abc");
        expected.add("array");
        assertEquals(expected, FilterByPrefix.filterByPrefix(strings, prefix));
    }
    
    @Test
        public void testNothing(){
            FilterByPrefix s = new FilterByPrefix();
            }
                                    
}