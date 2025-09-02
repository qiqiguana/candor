package original;

import java.util.Arrays;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
/**
* Test class of Solution1040.
*/
class Solution1040Test {
    @Test
    void testNumMovesStonesII() {
        Solution1040 solution = new Solution1040();
        int[] stones = {1, 2, 3, 4, 5};
        int[] result = solution.numMovesStonesII(stones);
        assertEquals(2, result.length);
    }
}