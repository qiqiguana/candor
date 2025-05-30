package original;

import java.util.Arrays;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
/**
* Test class of Solution3194.
*/
class Solution3194Test {
    @Test
    void testMinimumAverage()
    {
        Solution3194 sol = new Solution3194();
        int[] nums = {100000, 20000, 3000, 400, 40};
        double expected = 10200.0;
        assertEquals(expected,sol.minimumAverage(nums),1e-6);
    }
}