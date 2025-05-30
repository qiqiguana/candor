package original;

import java.util.ArrayDeque;

import java.util.Arrays;

import java.util.ArrayList;

import java.util.Deque;

import java.util.List;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
/**
* Test class of Solution2392.
*/
class Solution2392Test {
    @Test
void test_buildMatrix_k_3_rowConditions_1_colConditions_1() {
        // Arrange
Solution2392 solution = new Solution2392();
int k = 3;
int[][] rowConditions = {{1, 2}};
int[][] colConditions = {{2, 1}};
// Act
 int[][] result = solution.buildMatrix(k, rowConditions, colConditions);
// Assert
assertNotNull(result);
}
}