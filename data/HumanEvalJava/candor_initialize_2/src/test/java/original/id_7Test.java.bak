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
    void testFilterBySubstring_WhenInputListIsEmpty_ReturnsEmptyList() {
        List<Object> inputList = new ArrayList<>();
        String substring = "a";
        List<Object> expectedList = new ArrayList<>();
        assertEquals(expectedList, FilterBySubstring.filterBySubstring(inputList, substring));
    }
    
    @Test
        public void testNothing(){
            FilterBySubstring s = new FilterBySubstring();
            }
    @Test
    public void testFilterBySubstring_EmptyList_NonEmptySubstring() {
        List<Object> input = new ArrayList<>();
        String substring = "a";
        List<Object> expected = new ArrayList<>();
        assertEquals(expected, FilterBySubstring.filterBySubstring(input, substring));
    }
    @Test
    public void testFilterBySubstring_NullInputList() {
        List<Object> input = null;
        String substring = "a";
        assertThrows(NullPointerException.class, () -> FilterBySubstring.filterBySubstring(input, substring));
    }
    @Test
    public void testFilterBySubstring_withNullInput() {
        List<Object> input = null;
        String substring = "xyz";
        assertThrows(NullPointerException.class, () -> FilterBySubstring.filterBySubstring(input, substring));
    }
    @Test
    public void testFilterBySubstring_withNullInput_ThrowsNullPointerException() {
        List<Object> input = null;
        String substring = "a";
        assertThrows(NullPointerException.class, () -> FilterBySubstring.filterBySubstring(input, substring));
    }
                                    
}