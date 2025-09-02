package original;

import java.util.Arrays;

import java.util.List;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
/**
* Test class of Solution1595.
*/
class Solution1595Test {
    @Test
    void testConnectTwoGroups() {
        Solution1595 solution = new Solution1595();
        List<List<Integer>> cost = Arrays.asList(
            Arrays.asList(100, 19, 22, 9), 
            Arrays.asList(18, 14, 8, 16));
        int expected = 49;
        assertEquals(expected, solution.connectTwoGroups(cost));
    }
}