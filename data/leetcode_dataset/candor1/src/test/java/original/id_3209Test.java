package original;

import java.util.HashMap;

import java.util.Map;

import java.util.Set;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
/**
* Test class of Solution3209.
*/
class Solution3209Test {
    @Test
    void testCountSubarrays_1()
    {
        // Arrange
        Solution3209 solution = new Solution3209();
        int[] nums = {1, 2, 3, 4};
        int k = 2;

        // Act
        long actual = solution.countSubarrays(nums, k);
        long expected = 2;

        // Assert
        assertEquals(expected, actual);
    }
}