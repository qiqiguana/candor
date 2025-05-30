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
    
    @Test
        public void testNothing(){
            Solution1040 s = new Solution1040();
            }
    @Test
    public void test_numMovesStonesII_with_stones_that_have_a_gap_of_n_minus_2_v5() {
        Solution1040 solution = new Solution1040();
        int[] input = {1, 3, 4};
        int[] expectedOutput = {1, 1};
        assertArrayEquals(expectedOutput, solution.numMovesStonesII(input));
    }
                                    
}