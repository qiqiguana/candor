package original;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
/**
* Test class of Solution0555.
*/
class Solution0555Test {
    @Test
    void testSplitLoopedString() {
        String[] strs = {"abc","xyz"};
        Solution0555 solution = new Solution0555();
        String result = solution.splitLoopedString(strs);
        assertEquals("zyxcba", result);
    }
}