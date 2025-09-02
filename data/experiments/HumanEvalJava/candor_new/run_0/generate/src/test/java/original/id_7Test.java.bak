package original;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
/**
* Test class of FilterBySubstring.
*/
class FilterBySubstringTest {
    @Test
    void testFilterBySubstring_ContainsSubstring_ReturnsFilteredStrings() {
        List<Object> strings = new ArrayList<>();
        strings.add("abc");
        strings.add("bacd");
        strings.add("cde");
        strings.add("array");

        String substring = "a";
        List<Object> result = FilterBySubstring.filterBySubstring(strings, substring);

        assertEquals(3, result.size());
    }
}