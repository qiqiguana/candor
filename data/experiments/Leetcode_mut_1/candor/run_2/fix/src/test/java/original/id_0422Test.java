package original;

import java.util.List;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import java.util.ArrayList;
/**
* Test class of Solution0422.
*/
class Solution0422Test {
    @Test
    void testValidWordSquare() {
        Solution0422 solution = new Solution0422();
        List<String> words = new ArrayList<>();
        words.add("abc");
        words.add("bca");
        words.add("cab");
        assertTrue(solution.validWordSquare(words));
    }
    
    @Test
        public void testNothing(){
            Solution0422 s = new Solution0422();
            }
    @Test
    public void test_Words_Are_Not_Square_When_Out_Of_Bounds() {
        Solution0422 solution = new Solution0422();
        List<String> input = List.of("hello", "world");
        assertFalse(solution.validWordSquare(input));
    }
    @Test
    public void test_Words_Are_Not_Square_When_Characters_Do_Not_Match() {
        Solution0422 solution = new Solution0422();
        List<String> input = List.of("abcd", "dcba");
        assertFalse(solution.validWordSquare(input));
    }
    @Test
    public void test_Words_Are_Square_With_Empty_String() {
        Solution0422 solution = new Solution0422();
        List<String> input = List.of("");
        assertTrue(solution.validWordSquare(input));
    }
    @Test
    public void TestValidWordSquare_ReturnsFalse_WhenWordsAreNotSquared() {
        Solution0422 solution = new Solution0422();
        List<String> words = List.of("abc", "bda", "cbd");
        assertFalse(solution.validWordSquare(words));
    }
    @Test
    public void TestValidWordSquare_ReturnsFalse_WhenWordsHaveDifferentLengths() {
        Solution0422 solution = new Solution0422();
        List<String> words = List.of("abc", "bd");
        assertFalse(solution.validWordSquare(words));
    }
                                    
}