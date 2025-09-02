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
        List<List<Integer>> cost = Arrays.asList(Arrays.asList(1, 3, 5), Arrays.asList(2, 4, 6));
        Solution1595 solution1595 = new Solution1595();
        int result = solution1595.connectTwoGroups(cost);
        assertEquals(10, result);
    }
}
