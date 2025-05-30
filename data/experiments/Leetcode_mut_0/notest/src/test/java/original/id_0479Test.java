package original;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
/**
* Test class of Solution0479.
*/
class Solution0479Test {
    @Test
    void testLargestPalindrome() {
        Solution0479 solution = new Solution0479();
        int result = solution.largestPalindrome(1);
        assertEquals(9, result);
    }
}