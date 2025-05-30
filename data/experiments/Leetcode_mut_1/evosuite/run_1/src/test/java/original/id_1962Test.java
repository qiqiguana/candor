package original;

import java.util.PriorityQueue;

import java.util.Queue;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
/**
* Test class of Solution1962.
*/
class Solution1962Test {
    @Test
    void testMinStoneSum() {
        Solution1962 solution = new Solution1962();
        int[] piles = {3, 5};
        int k = 1;
        assertEquals(6, solution.minStoneSum(piles, k));
    }
}