package original;

import java.util.HashSet;

import java.util.Set;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
/**
* Test class of Solution1521.
*/
class Solution1521Test {
    @Test
    void testClosestToTarget() {
        Solution1521 solution = new Solution1521();
        int[] arr = {1000000, 10000, 1000, 100};
        int target = 100;
        int expected = 0;
        assertEquals(expected, solution.closestToTarget(arr, target));
    }
}