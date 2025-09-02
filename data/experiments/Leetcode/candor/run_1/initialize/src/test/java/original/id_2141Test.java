package original;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
/**
* Test class of Solution2141.
*/
class Solution2141Test {
    @Test
    void testMaxRunTime() {
        Solution2141 solution = new Solution2141();
        int n = 2;
        int[] batteries = {3, 3, 3};
        long expected = 4;
        long actual = solution.maxRunTime(n, batteries);
        assertEquals(expected, actual);
    }
}