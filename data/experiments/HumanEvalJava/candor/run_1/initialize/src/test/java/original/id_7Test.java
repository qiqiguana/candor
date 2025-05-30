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
    void testFilterBySubstring_WhenListIsEmpty_ReturnsEmptyList() {
        List<Object> input = new ArrayList<>();
        String substring = "a";
        List<Object> expected = new ArrayList<>();
        assertEquals(expected, FilterBySubstring.filterBySubstring(input, substring));
    }
 
 @Test
     public void testNothing(){
         FilterBySubstring s = new FilterBySubstring();
         }
 @Test
 void test_FilterBySubstring_Instantiation() {
     FilterBySubstring filter = new FilterBySubstring();
     assertNotNull(filter);
 }
 @Test
 void test_filterBySubstring_EmptyList() {
     List<Object> input = new ArrayList<>();
     String substring = "a";
     List<Object> result = FilterBySubstring.filterBySubstring(input, substring);
     assertTrue(result.isEmpty());
 }
 @Test
 public void testFilterBySubstring_SubstringPresentInSomeStrings() {
     List<Object> input = new ArrayList<>();
     input.add("abc");
     input.add("bacd");
     input.add("cde");
     input.add("array");
     String substring = "a";
     List<Object> expectedOutput = new ArrayList<>();
     expectedOutput.add("abc");
     expectedOutput.add("bacd");
     expectedOutput.add("array");
     assertEquals(expectedOutput, FilterBySubstring.filterBySubstring(input, substring));
 }
                                 
}