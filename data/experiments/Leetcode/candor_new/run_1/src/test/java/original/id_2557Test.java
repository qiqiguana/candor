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
        Solution2557 solution = new Solution2557();
        int[] banned = {3, 4};
        int n = 6;
        long maxSum = 10;
        assertEquals(3, solution.maxCount(banned, n, maxSum));
    }
}