package original;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
/**
* Test class of MatchParens.
*/
class MatchParensTest {

    @Test
    void test_matchParens_EmptyStrings_ReturnsYes() {
        List<String> input = List.of("", "");
        String result = MatchParens.matchParens(input);
        assertEquals("Yes", result);
    }
    
    @Test
        public void testNothing(){
            MatchParens s = new MatchParens();
            }
    @Test
    public void test_Match_Parens_With_Two_Partially_Unbalanced_Strings() {
        List<String> input = Arrays.asList(")(()", "(()(");
        String expected = "No";
        assertEquals(expected, MatchParens.matchParens(input));
    }
    @Test
    public void test_Match_Parens_With_Empty_Strings() {
        List<String> input = Arrays.asList("", "");
        String expected = "Yes";
        assertEquals(expected, MatchParens.matchParens(input));
    }
    @Test
    public void test_Match_Parens_With_Two_Partially_Unbalanced_Strings2() {
        List<String> input = java.util.Arrays.asList(")(()", "(()(");
        String expected = "No";
        assertEquals(expected, MatchParens.matchParens(input));
    }
    @Test
    public void Test_Sad_Path_Null_Input() {
        assertThrows(NullPointerException.class, () -> MatchParens.matchParens(null));
    }
    @Test
    public void test_MatchParens_With_Balanced_Strings() {
    	List<String> input = Arrays.asList("(())", "(()())");
    	assertEquals("Yes", MatchParens.matchParens(input));
    }
    @Test
    public void test_MatchParens_Edge_Case_Reversed_Strings() {
    	List<String> input = Arrays.asList(")", "(");
    	assertEquals("Yes", MatchParens.matchParens(input));
    }
    @Test
    public void test_MatchParens_Edge_Case_Same_String_Concatenation() {
    	List<String> input = Arrays.asList("()", "()");
    	assertEquals("Yes", MatchParens.matchParens(input));
    }
    @Test
    public void test_MatchParens_Edge_Case_Long_Balanced_Strings() {
    	List<String> input = Arrays.asList("(((())))", "(((())))");
    	assertEquals("Yes", MatchParens.matchParens(input));
    }
    @Test
    public void test_MatchParens_Edge_Case_Long_Unbalanced_Strings() {
    	List<String> input = Arrays.asList(")(()))", "((()))(");
    	assertEquals("No", MatchParens.matchParens(input));
    }
    @Test
    public void test_MatchParens_With_Same_String() {
    	List<String> input = Arrays.asList("()", "()");
    	assertEquals("Yes", MatchParens.matchParens(input));
    }
    @Test
    public void test_MatchParens_With_Balanced_Strings_1() {
    	List<String> input = Arrays.asList("(())", "(()())");
    	assertEquals("Yes", MatchParens.matchParens(input));
    }
    @Test
    public void Test_Empty_List_Fixed() {
        List<String> lst = new ArrayList<>();
        assertThrows(IndexOutOfBoundsException.class, () -> MatchParens.matchParens(lst));
    }
    @Test
    public void Test_MatchParens_With_Unbalanced_Strings2() {
        List<String> input = Arrays.asList("(()", ")())");
        assertEquals("No", MatchParens.matchParens(input));
    }
                                    
}