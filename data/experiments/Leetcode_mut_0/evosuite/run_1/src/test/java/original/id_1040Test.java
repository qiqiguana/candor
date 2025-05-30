package original;

import java.util.Arrays;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
* Test class of Solution1040.
*/
class Solution1040Test {
    @Test
    void test_numMovesStonesII() {
        Solution1040 solution = new Solution1040();
        int[] stones = {4,3,2,1};
        int[] expectedOutput = {0, 0};
        assertArrayEquals(expectedOutput, solution.numMovesStonesII(stones));
    }
}