package oracle1;

import static org.junit.jupiter.api.Assertions.assertEquals;
import java.util.Arrays;
import java.util.List;
import java.util.ArrayList;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
/**
* Test class of MatchParens.
*/
class MatchParensTest {

    @Test
    void testMatchParens_BalancedStrings_ReturnsYes() {
        List<String> input = new ArrayList<>();
        input.add("(");
        input.add(")");
        String result = MatchParens.matchParens(input);
        assertEquals("Yes", result);
    }
    
    @Test
     void testNothing(){
         MatchParens s = new MatchParens();
         }
    @Test
    void test_matchParens_NullInput_ThrowsNullPointerException5() {
        assertThrows(NullPointerException.class, () -> MatchParens.matchParens(null));
    }
    @Test
    void test_matchParens_EmptyStrings_ReturnsYes() {
        List<String> list = new ArrayList<>();
        list.add("");
        list.add("");
        String result = MatchParens.matchParens(list);
        assertEquals("Yes", result);
    }
    @Test
    void test_matchParens_OnlyOpenParenthesesInFirstString_ReturnsYes1() {
        List<String> list = new ArrayList<>();
        list.add(")(()");
        list.add("((())");
        String result = MatchParens.matchParens(list);
        assertEquals("No", result);
    }
    @Test
    void test_matchParens_OnlyOpenParenthesesInSecondString_ReturnsYes3() {
        List<String> list = new ArrayList<>();
        list.add(")(()");
        list.add("((())");
        String result = MatchParens.matchParens(list);
        assertEquals("No", result);
    }
    @Test
    void test_matchParens_OnlyOpenParenthesesInFirstString_ReturnsYes6() {
        List<String> list = new ArrayList<>();
        list.add(")(()");
        list.add("((())");
        String result = MatchParens.matchParens(list);
        assertEquals("No", result);
    }
    @Test
    void testMatchParensNothing() {
        MatchParens s = new MatchParens();
        assertNotNull(s);
    }
    @Test
    public void test_empty_strings() {
        List<String> lst = Arrays.asList("", "");
        assertEquals("Yes", MatchParens.matchParens(lst));
    }
    @Test
    public void test_single_open_parenthesis_1() {
        List<String> lst = Arrays.asList("(", "");
        assertEquals("No", MatchParens.matchParens(lst));
    }
    @Test
    public void test_single_close_parenthesis() {
        List<String> lst = Arrays.asList("", ")");
        assertEquals("No", MatchParens.matchParens(lst));
    }
    @Test
    public void test_unbalanced_parentheses_1() {
        List<String> lst = Arrays.asList("(", ")");
        assertEquals("Yes", MatchParens.matchParens(lst));
    }
    @Test
    public void Test_MatchParens_EdgeCase_OpenParenthesesOnly() {
        List<String> input = Arrays.asList("(", "(");
        assertEquals("No", MatchParens.matchParens(input));
    }
    @Test
    public void Test_MatchParens_SpecificFunctionality_NullInput() {
        assertThrows(NullPointerException.class, () -> MatchParens.matchParens(null));
    }
    @Test
    void testMatchParensInitialization1(){
        MatchParens s = new MatchParens();
        assertNotNull(s);
    }
    @Test
    public void TestMatchParens_HappyPath() {
        List<String> lst = new ArrayList<>();
        lst.add("()(");
        lst.add(")");
        assertEquals("Yes", MatchParens.matchParens(lst));
    }
    @Test
    public void TestMatchParens_SadPath() {
        List<String> lst = new ArrayList<>();
        lst.add(")");
        lst.add(")");
        assertEquals("No", MatchParens.matchParens(lst));
    }
    @Test
    public void TestMatchParens_EdgeCase_OpeningParenthesesOnly() {
        List<String> lst = new ArrayList<>();
        lst.add("(");
        lst.add("(");
        assertEquals("No", MatchParens.matchParens(lst));
    }
    @Test
    public void TestMatchParens_EdgeCase_ClosingParenthesesOnly() {
        List<String> lst = new ArrayList<>();
        lst.add(")");
        lst.add(")");
        assertEquals("No", MatchParens.matchParens(lst));
    }
    @Test
    public void TestMatchParens_SpecificFunctionality_EmptyStrings() {
        List<String> lst = new ArrayList<>();
        lst.add("");
        lst.add("");
        assertEquals("Yes", MatchParens.matchParens(lst));
    }
    @Test
    public void TestMatchParens_SpecificFunctionality_NullInput() {
        List<String> lst = null;
        assertThrows(NullPointerException.class, () -> MatchParens.matchParens(lst));
    }
    @Test
    public void testEmptyStrings() {
        List<String> input = Arrays.asList("", "");
        assertEquals(MatchParens.matchParens(input), "Yes");
    }
    @Test
    public void testDifferentOrder() {
        List<String> input = Arrays.asList("(()", "())");
        assertEquals(MatchParens.matchParens(input), "Yes");
    }
    @Test
    void testMatchParensEmptyStrings1() {
        List<String> input = new ArrayList<>();
        input.add("");
        input.add("");
        assertEquals("Yes", MatchParens.matchParens(input));
    }
    @Test
    void testMatchParensEmptyStrings() {
        List<String> input = new ArrayList<>();
        input.add("");
        input.add("");
        assertEquals("Yes", MatchParens.matchParens(input));
    }
    @Test
    void testEmptyStrings2() {
        List<String> input = new ArrayList<>();
        input.add("");
        input.add("");
        assertEquals("Yes", MatchParens.matchParens(input));
    }
    @Test
    void testEmptyStrings1() {
        List<String> input = new ArrayList<>();
        input.add("");
        input.add("");
        assertEquals("Yes", MatchParens.matchParens(input));
    }
    @Test
    public void testMatchParens_EmptyStrings_Fixed() {
        List<String> input = List.of("", "");
        String result = MatchParens.matchParens(input);
        assertEquals("Yes", result);
    }
    @Test
    public void testMatchParens_EmptyStrings_Fixed_1() {
        List<String> input = List.of("", "");
        String result = MatchParens.matchParens(input);
        assertEquals(result, "Yes");
    }
    @Test
    public void testMatchParens_OnlyOpenParentheses2() {
        List<String> input = List.of("(", "(");
        assertEquals("No", MatchParens.matchParens(input));
    }
    @Test
    public void testMatchParens_OnlyCloseParentheses_1() {
        List<String> input = List.of(")", ")");
        String result = MatchParens.matchParens(input);
        assertEquals("No", result);
    }
    @Test
    public void testMatchParens_LargeBalancedParentheses_1() {
        List<String> input = List.of("(()())(()())", "(()())(()())");
        String result = MatchParens.matchParens(input);
        assertEquals(result, "Yes");
    }
    @Test
    public void Test1() {
        List<String> input = Arrays.asList("()", "()");
        String expected = "Yes";
        assertEquals(expected, MatchParens.matchParens(input));
    }
    @Test
    public void Test2() {
        List<String> input = Arrays.asList(")", ")");
        String expected = "No";
        assertEquals(expected, MatchParens.matchParens(input));
    }
    @Test
    public void Test4() {
        List<String> input = Arrays.asList(")(()", "(()(");
        String expected = "No";
        assertEquals(expected, MatchParens.matchParens(input));
    }
    @Test
    public void Test5() {
        List<String> input = Arrays.asList(")", "(");
        String expected = "Yes";
        assertEquals(expected, MatchParens.matchParens(input));
    }
    @Test
    void testMatchParens(){
        List<String> list = new ArrayList<>();
        list.add("(");
        list.add(")");
        assertEquals("Yes", MatchParens.matchParens(list));
    }
                                  
}