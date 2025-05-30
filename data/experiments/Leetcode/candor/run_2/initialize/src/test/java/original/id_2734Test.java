package original;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
/**
* Test class of Solution2734.
*/
class Solution2734Test {
    @Test
    void testSmallestString() {
        Solution2734 solution = new Solution2734();
        String input = "aaa";
        String expectedOutput = "aaz";
        assertEquals(expectedOutput, solution.smallestString(input));
    }
}