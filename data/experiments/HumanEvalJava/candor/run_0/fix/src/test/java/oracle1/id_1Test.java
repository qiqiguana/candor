package oracle1;

import java.util.Arrays;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
/**
* Test class of SeparateParenGroups.
*/
class SeparateParenGroupsTest {
    @Test
    void testSeparateParenGroups_SimpleInput() {
        // given
        String input = "(()()) ((())) () ((())()())";
        List<String> expected = new ArrayList<>();
        expected.add("(()())");
        expected.add("((()))");
        expected.add("()");
        expected.add("((())()())");

        // when
        List<String> result = SeparateParenGroups.separateParenGroups(input);

        // then
        assertEquals(expected, result);
    }
    
    @Test
     void testNothing(){
         SeparateParenGroups s = new SeparateParenGroups();
         }
    @Test
    public void testSeparateParenGroupsNullInput() {
        String input = null;
        assertThrows(NullPointerException.class, () -> SeparateParenGroups.separateParenGroups(input));
    }
    @Test
    public void testSeparateParenGroupsHappyPath() {
        String input = "(()()) ((())) () ((())()())";
        List<String> expectedResult = Arrays.asList("(()())", "((()))", "()", "((())()())");
        List<String> actualResult = SeparateParenGroups.separateParenGroups(input);
        assertEquals(expectedResult, actualResult);
    }
                                  
}