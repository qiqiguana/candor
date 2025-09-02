package original;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
/**
* Test class of Solution0942.
*/
class Solution0942Test {
    @Test
    void test_diStringMatch() {
        Solution0942 solution = new Solution0942();
        int[] result = solution.diStringMatch("IDID");
        assertArrayEquals(new int[]{0, 4, 1, 3, 2}, result);
    }
}