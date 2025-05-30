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
    
    @Test
        public void testNothing(){
            Solution0479 s = new Solution0479();
            }
    @Test
    public void testLargestPalindrome_n_1() {
        Solution0479 solution = new Solution0479();
        int actual = solution.largestPalindrome(1);
        assertEquals(9, actual);
    }
    @Test
    public void edgeCaseTestNIs1() {
        Solution0479 solution = new Solution0479();
        int result = solution.largestPalindrome(1);
        assertEquals(9, result);
    }
    @Test
    public void testLargestPalindrome_n1() {
        Solution0479 solution = new Solution0479();
        int actual = solution.largestPalindrome(1);
        assertEquals(9, actual);
    }
    @Test
    public void testLargestPalindrome_n2() {
        Solution0479 solution = new Solution0479();
        int actual = solution.largestPalindrome(2);
        assertEquals(987, actual);
    }
                                    
}