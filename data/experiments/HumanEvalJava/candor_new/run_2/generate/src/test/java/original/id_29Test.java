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
    void testFilterByPrefix_withPrefixXXX_shouldReturnMatchingStrings() {
        // Given
        List<Object> inputList = new ArrayList<>();
        inputList.add("xxx");
        inputList.add("asd");
        inputList.add("xxy");
        inputList.add("john doe");
        inputList.add("xxxAAA");
        inputList.add("xxx");

        String prefix = "xxx";

        // When
        List<Object> result = FilterByPrefix.filterByPrefix(inputList, prefix);

        // Then
        assertEquals(3, result.size());
    }
}