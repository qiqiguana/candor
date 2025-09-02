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
}