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
            Arrays.asList(1, 3, 5),
            Arrays.asList(4, 6, 8)
        );
        int actual = solution.connectTwoGroups(cost);
        int expected = 12;
        assertEquals(expected, actual);
    }
}