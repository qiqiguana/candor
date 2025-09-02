package original;

import java.util.ArrayList;

import java.util.List;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
/**
* Test class of Solution1102.
*/
class Solution1102Test {

    @Test
    void testMaximumMinimumPath() {
        // Arrange
        int[][] grid = {{1,1},{1,1}};
        Solution1102 solution = new Solution1102();
        // Act and Assert
        assertEquals(1,solution.maximumMinimumPath(grid));
    }
}