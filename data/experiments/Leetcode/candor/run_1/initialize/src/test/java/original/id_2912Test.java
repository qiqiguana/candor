package original;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
/**
* Test class of Solution2912.
*/
class Solution2912Test {
    @Test
    void testNumberOfWays_SourceEqualsDest_ReturnsF0() {
        // Arrange
        Solution2912 solution = new Solution2912();
        int n = 3;
        int m = 4;
        int k = 1;
        int[] source = {1, 1};
        int[] dest = {1, 1};

        // Act and Assert
        assertEquals(0, solution.numberOfWays(n, m, k, source, dest));
    }
}
