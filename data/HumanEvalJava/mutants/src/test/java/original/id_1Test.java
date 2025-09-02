package original;

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
    void separateParenGroups_SimpleCase_GroupsSeparated() {
        String input = "(()) () ((()))";
        List<String> expected = new ArrayList<>();
        expected.add("(())");
        expected.add("()");
        expected.add("((()))");
        assertEquals(expected, SeparateParenGroups.separateParenGroups(input));
    }
    
    @Test
        public void testNothing(){
            SeparateParenGroups s = new SeparateParenGroups();
            }
    @Test
    public void test_separate_paren_groups_empty_string() {
        String parenString = "";
        List<String> expectedResult = new ArrayList<>();
        List<String> result = SeparateParenGroups.separateParenGroups(parenString);
        assertEquals(expectedResult, result);
    }
    @Test
    public void test_separate_paren_groups_null_input() {
        String parenString = null;
        assertThrows(NullPointerException.class, () -> SeparateParenGroups.separateParenGroups(parenString));
    }
    @Test
    public void test_separate_paren_groups_happy_path() {
        String parenString = "(()()) ((())) () ((())()())";
        List<String> expectedResult = Arrays.asList("(()())", "((()))", "()", "((())()())");
        List<String> result = SeparateParenGroups.separateParenGroups(parenString);
        assertEquals(expectedResult, result);
    }
                                    
}