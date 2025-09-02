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
    void test_maxOutput() {
        Solution2538 solution = new Solution2538();
        int n = 3;
        int[][] edges = {{0,1},{1,2}};
        int[] price = {1,1,1};
        long expected = 2;
        long actual = solution.maxOutput(n, edges, price);
        assertEquals(expected, actual);
    }

}