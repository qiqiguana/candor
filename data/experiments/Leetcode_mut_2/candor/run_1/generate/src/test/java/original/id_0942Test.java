package original;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
/**
* Test class of Solution0942.
*/
class Solution0942Test {
    @Test
    void test_diStringMatch_I_only() {
        // Arrange
        Solution0942 solution = new Solution0942();
        String input = "II";

        // Act
        int[] result = solution.diStringMatch(input);

        // Assert
        assertArrayEquals(new int[]{0, 1, 2}, result);
    }
}