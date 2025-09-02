package original;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
/**
* Test class of Solution0555.
*/
class Solution0555Test {
    @Test
    void testSplitLoopedString_0() {
        Solution0555 solution = new Solution0555();
        String[] strs = {"abc","bac"};
        String expected = "ccbaba";
        assertEquals(expected, solution.splitLoopedString(strs));
    }
    @Test
    public void testCompareReversedString21() {
        Solution0555 solution = new Solution0555();
        String[] strs = {"abc", "xyz"};
        String expected = "zyxcba";
        for (int i = 0; i < strs.length; ++i) {
            String s = strs[i];
            String t = new StringBuilder(s).reverse().toString();
            if (s.compareTo(t) < 0) {
                strs[i] = t;
            }
        }
        String actual = solution.splitLoopedString(strs);
        assertEquals(expected, actual);
    }
}