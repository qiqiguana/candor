package original;

import java.util.Arrays;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
/**
* Test class of Solution3250.
*/
class Solution3250Test {
    @Test
    void testCountOfPairs()
    {
        // Arrange
        int[] nums = {1, 2, 3};
        Solution3250 solution = new Solution3250();
        int expected = 4;

        // Act
        int actual = solution.countOfPairs(nums);

        // Assert
        assertEquals(expected, actual);
    }
}
