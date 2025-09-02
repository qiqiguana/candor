package original;

import java.util.Arrays;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
/**
* Test class of Solution3219.
*/
class Solution3219Test {

    @Test
    void testMinimumCost_MultipleCuts_HorizontalCutFirst() {
        Solution3219 solution = new Solution3219();
        int m = 3, n = 2;
        int[] horizontalCut = {1, 2};
        int[] verticalCut = {1};
        long expected = 6L;
        assertEquals(expected, solution.minimumCost(m, n, horizontalCut, verticalCut));
    }
}