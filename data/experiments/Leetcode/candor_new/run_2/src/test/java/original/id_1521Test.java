package original;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import java.util.HashSet;
import java.util.Set;

/**
* Test class of Solution1521.
*/
class Solution1521Test {
    @Test
    void testClosestToTarget() {
        Solution1521 solution = new Solution1521();
        int[] arr = {1000000, 10000, 1000, 100};
        int target = 100;
        assertEquals(0, solution.closestToTarget(arr, target));
    }
}