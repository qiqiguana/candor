package original;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
/**
* Test class of Solution0548.
*/
class Solution0548Test {
    @Test
    void testSplitArray() {
        Solution0548 solution = new Solution0548();
        boolean actual = solution.splitArray(new int[]{1, 2, 3});
        assertFalse(actual);
    }
}
