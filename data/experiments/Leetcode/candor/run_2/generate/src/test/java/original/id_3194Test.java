package original;

import java.util.Arrays;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
/**
* Test class of Solution3194.
*/
class Solution3194Test {
    @Test
    void testMinimumAverage_ReturnsMinSum() {
        // Arrange
        Solution3194 solution = new Solution3194();
        int[] nums = {1, 3, 5};
        double expected = 3;
        
        // Act
        double actual = solution.minimumAverage(nums);
        
        // Assert
        assertEquals(expected, actual);
    }
}
