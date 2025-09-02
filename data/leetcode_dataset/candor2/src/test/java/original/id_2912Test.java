package original;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
/**
* Test class of Solution2912.
*/
class Solution2912Test {
    @Test
    void testnumberOfWays_SameRow_DifferentColumns() {
        // Arrange
        Solution2912 solution = new Solution2912();
        int n = 5;
        int m = 3;
        int k = 1;
        int[] source = {0, 0};
        int[] dest = {0, 1};

        // Act
        int result = solution.numberOfWays(n, m, k, source, dest);

        // Assert
        assertEquals(1, result);
    }
}
