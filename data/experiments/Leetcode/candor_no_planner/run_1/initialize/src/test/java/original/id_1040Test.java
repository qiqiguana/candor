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
        int[] stones = {3, 2, 10, 4};
        int[] expectedOutput = {2, 5};
        assertArrayEquals(expectedOutput, solution.numMovesStonesII(stones));
    }
}