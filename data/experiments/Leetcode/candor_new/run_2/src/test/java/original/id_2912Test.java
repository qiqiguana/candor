package original;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
/**
* Test class of Solution2912.
*/
class Solution2912Test {

    @Test
    void numberOfWays_SourceEqualsDest_ReturnsCorrectResult() {
        // Arrange
        Solution2912 solution = new Solution2912();
        int n = 10;
        int m = 10;
        int k = 3;
        int[] source = {1, 1};
        int[] dest = {1, 1};

        // Act
        int result = solution.numberOfWays(n, m, k, source, dest);

        // Assert
        assertEquals(144, result);
    }
}