package original;

import java.util.Arrays;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
/**
* Test class of Solution2538.
*/
class Solution2538Test {
    @Test
    void test_maxOutput_should_ReturnExpectedResult_WhenCalledWithValidInput() {
        // Arrange
        int n = 3;
        int[][] edges = new int[][] {{0,1},{1,2}};
        int[] price = new int[] {1,1,1};
        Solution2538 solution = new Solution2538();
        long expected = 2;

        // Act
        long actual = solution.maxOutput(n, edges, price);

        // Assert
        assertEquals(expected, actual);
    }
}