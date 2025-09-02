package oracle1;

import java.util.Arrays;
import java.util.ArrayList;

import java.util.List;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
/**
* Test class of FilterByPrefix.
*/
class FilterByPrefixTest {
    @Test
    void testFilterByPrefix_WithStringsStartingWithGivenPrefix_ReturnsExpectedResult() {
        // Arrange
        List<Object> inputList = new ArrayList<>();
        inputList.add("abc");
        inputList.add("bcd");
        inputList.add("cde");
        inputList.add("array");
        String prefix = "a";

        // Act
        List<Object> result = FilterByPrefix.filterByPrefix(inputList, prefix);

        // Assert
        assertEquals(2, result.size());
    }
    
    @Test
        void testNothing(){
            FilterByPrefix s = new FilterByPrefix();
            }
    @Test
    public void testEmptyInputList() {
        List<Object> strings = new ArrayList<>();
        String prefix = "a";
        List<Object> expected = new ArrayList<>();
        assertEquals(expected, FilterByPrefix.filterByPrefix(strings, prefix));
    }
    @Test
    public void testNullInputList() {
        List<Object> strings = null;
        String prefix = "a";
        assertThrows(IllegalArgumentException.class, () -> FilterByPrefix.filterByPrefix(strings, prefix));
    }
    @Test
    public void testNoMatches() {
        List<Object> strings = new ArrayList<>(java.util.Arrays.asList("abc", "bcd", "cde"));
        String prefix = "z";
        List<Object> expected = new ArrayList<>();
        assertEquals(expected, FilterByPrefix.filterByPrefix(strings, prefix));
    }
                                    
}