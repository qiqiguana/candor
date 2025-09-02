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
        int[] piles = {5,4,9};
        int k = 2;
        int expected = 12;
        int actual = solution.minStoneSum(piles, k);
        assertEquals(expected, actual);
    }
}
