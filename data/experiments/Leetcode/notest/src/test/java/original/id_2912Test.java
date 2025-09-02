package original;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
/**
* Test class of Solution2912.
*/
class Solution2912Test {

    @Test
    void testNumberOfWays_SourceEqualsDest_ReturnsF0_01() {
        // Arrange
        Solution2912 solution = new Solution2912();
        int n = 10;
        int m = 20;
        int k = 1;
        int[] source = {1, 1};
        int[] dest = {1, 1};

        // Act
        int result = solution.numberOfWays(n, m, k, source, dest);

        // Assert
        assertNotEquals(1, result);
    }
}