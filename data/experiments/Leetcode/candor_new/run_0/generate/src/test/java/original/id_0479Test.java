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
        assertEquals(9, solution.largestPalindrome(1));
    }
    @Test public void test_largest_palindrome_n_2() { Solution0479 s = new Solution0479(); assertEquals(987, s.largestPalindrome(2)); }
}