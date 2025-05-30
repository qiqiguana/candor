package original;

import java.util.Arrays;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
/**
* Test class of Solution2538.
*/
class Solution2538Test {
    @Test
    void testMaxOutput_0() {
        Solution2538 solution = new Solution2538();
        int n = 6;
        int[][] edges = {{0, 1}, {1, 2}, {0, 3}, {3, 4}, {3, 5}};
        int[] price = {9, 7, 10, 5, 3, 12};
        long expected = 33;
        long actual = solution.maxOutput(n, edges, price);
        assertEquals(expected, actual);
    }
}