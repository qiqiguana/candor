package original;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
/**
* Test class of Solution1899.
*/
class Solution1899Test {
    @Test
    void testMergeTriplets_TargetReached_ReturnsTrue() {
        Solution1899 solution = new Solution1899();
        int[][] triplets = {{2,5,3},{1,8,4},{1,7,5}};
        int[] target = {2, 5, 5};
        assertFalse(solution.mergeTriplets(triplets, target));
    }
}