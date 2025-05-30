package original;

import java.util.ArrayDeque;

import java.util.Arrays;

import java.util.ArrayList;

import java.util.Deque;

import java.util.List;


import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
/**
* Test class of Solution2392.
*/
class Solution2392Test {
    @Test
    void testBuildMatrix() {
        Solution2392 solution = new Solution2392();
        int[][] result = solution.buildMatrix(3, new int[][]{{1, 2}, {3, 2}}, new int[][]{{2, 1}, {3, 2}});
        assertNotNull(result);
    }
}
