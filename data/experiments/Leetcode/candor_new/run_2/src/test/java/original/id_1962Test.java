package original;

import java.util.PriorityQueue;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
/**
* Test class of Solution1962.
*/
class Solution1962Test {
    @Test
    void testMinStoneSum() {
        Solution1962 solution = new Solution1962();
        int[] piles = {3,5,7,9};
        int k = 2;
        assertEquals(17,solution.minStoneSum(piles,k));
    }
}