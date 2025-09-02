package original;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
/**
* Test class of Solution1021.
*/
class Solution1021Test {
    @Test
    void testRemoveOuterParentheses() {
        Solution1021 solution = new Solution1021();
        String input = "(()())(())";
        String expected = "()()()";
        assertEquals(expected, solution.removeOuterParentheses(input));
    }
}