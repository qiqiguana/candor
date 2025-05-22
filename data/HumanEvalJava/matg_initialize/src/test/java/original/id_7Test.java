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
    void filterBySubstring_ContainsSubString_ReturnsMatchedStrings() {
        // Arrange
        List<Object> strings = new ArrayList<>(List.of("abc", "bacd", "cde", "array"));
        String substring = "a";

        // Act
        List<Object> result = FilterBySubstring.filterBySubstring(strings, substring);

        // Assert
        assertEquals(List.of("abc", "bacd", "array"), result);
    }
}