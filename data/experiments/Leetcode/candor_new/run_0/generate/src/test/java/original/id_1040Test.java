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
        int[] expected = {0, 0};
        assertArrayEquals(expected, solution.numMovesStonesII(stones));
    }
    @Test
    public void test_numMovesStonesII_correctCalculation() {
        Solution1040 s = new Solution1040();
        int[] stones = {7,4,9};
        int[] expectedMinAndMaxMoves = {1,2};
        int[] actualMinAndMaxMoves = s.numMovesStonesII(stones);
        assertArrayEquals(expectedMinAndMaxMoves, actualMinAndMaxMoves);
    }
}