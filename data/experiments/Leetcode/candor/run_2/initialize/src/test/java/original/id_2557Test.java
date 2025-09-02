package original;

import java.util.Collections;

import java.util.ArrayList;

import java.util.HashSet;

import java.util.List;

import java.util.Set;


import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
/**
* Test class of Solution2557.
*/
class Solution2557Test {
    @Test
    void testMaxCount() {
        int[] banned = {3, 2, 5, 7};
        int n = 10;
        long maxSum = 14;
        Solution2557 solution = new Solution2557();
        assertEquals(3, solution.maxCount(banned, n, maxSum));
    }
}