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
    void testMaximumMinimumPath1() {
        // Create an instance of the class to be tested
        Solution1102 solution = new Solution1102();

        // Define a 3x3 grid with values ranging from 1 to 9
        int[][] grid = {{5,4,5},{1,0,0},{4,3,1}};

        // Call the method to be tested and store the result
        int result = solution.maximumMinimumPath(grid);

        // Assert that the expected result is equal to 1
        assertEquals(1, result);
    }
}
