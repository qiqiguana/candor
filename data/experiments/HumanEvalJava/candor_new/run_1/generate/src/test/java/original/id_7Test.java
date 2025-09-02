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
    void filterBySubstring_returnExpectedList_WhenInputListHasElementsThatContainSubstring() {
        List<Object> input = new ArrayList<>();
        input.add("abc");
        input.add("bacd");
        input.add("cde");
        input.add("array");

        String substring = "a";
        List<Object> expected = new ArrayList<>();
        expected.add("abc");
        expected.add("bacd");
        expected.add("array");

        List<Object> result = FilterBySubstring.filterBySubstring(input, substring);
        assertEquals(expected, result);
    }
    
    @Test
        public void testNothing(){
            FilterBySubstring s = new FilterBySubstring();
            }
                                    
}