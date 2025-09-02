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
    void testFilterByPrefix_PrefixMatchesMultipleStrings_ReturnsMatchingStrings() {
        // Arrange
        List<Object> input = new ArrayList<>();
        input.add("abc");
        input.add("bcd");
        input.add("cde");
        input.add("array");
        String prefix = "a";

        // Act
        List<Object> result = FilterByPrefix.filterByPrefix(input, prefix);

        // Assert
        assertEquals(2, result.size());
    }
}
