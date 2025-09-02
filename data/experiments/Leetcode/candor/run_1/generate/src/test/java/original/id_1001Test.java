package original;

import java.util.HashMap;

import java.util.HashSet;

import java.util.Map;

import java.util.Set;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
/**
* Test class of Solution1001.
*/
class Solution1001Test {
    @Test
    void testGridIllumination() {
        // Arrange
        Solution1001 solution = new Solution1001();
        int n = 5;
        int[][] lamps = {{0, 0}, {4, 4}};
        int[][] queries = {{1, 1}, {1, 0}};
        
        // Act
        int[] result = solution.gridIllumination(n, lamps, queries);
        
        // Assert
        assertArrayEquals(new int[]{1, 0}, result);
    }
}