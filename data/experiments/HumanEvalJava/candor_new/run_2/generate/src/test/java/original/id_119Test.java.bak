package original;

import java.util.Arrays;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
/**
* Test class of MatchParens.
*/
class MatchParensTest {
    @Test
    void testMatchParensYes1() {
        List<String> list = new ArrayList<>();
        list.add("(()(())");
        list.add(")())()");
        assertEquals("No", MatchParens.matchParens(list));
    }
    
    @Test
        public void testNothing(){
            MatchParens s = new MatchParens();
            }
    @Test
    public void testClassInitializationWithValidInput1() {
        String result = MatchParens.matchParens(List.of("(", ")"));
        assertNotNull(result);
    }
    @Test
    public void test_MatchParens_with_balanced_and_imbalanced_strings_1() {
        List<String> input = Arrays.asList(")(()",
                "(()(");
        String result = MatchParens.matchParens(input);
        assertEquals("No", result);
    }
    @Test
    public void TestMatchParensBalancedParenthesesInBothStrings2() {
        List<String> lst = Arrays.asList("((())", "(()())");
        int s1OpenCount = countChar(lst.get(0), '(');
        int s1CloseCount = countChar(lst.get(0), ')');
        int s2OpenCount = countChar(lst.get(1), '(');
        int s2CloseCount = countChar(lst.get(1), ')');
    
        boolean canFormBalancedString = (s1OpenCount + s2OpenCount) == (s1CloseCount + s2CloseCount);
        if (canFormBalancedString) {
            assertEquals("Yes", MatchParens.matchParens(lst));
        } else {
            assertEquals("No", MatchParens.matchParens(lst));
        }
    }
    
    private int countChar(String str, char c) {
        int count = 0;
        for (int i = 0; i < str.length(); i++) {
            if (str.charAt(i) == c) {
                count++;
            }
        }
        return count;
    }
                                    
}