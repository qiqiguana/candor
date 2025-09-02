package original;

import java.util.Arrays;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
/**
* Test class of Solution1449.
*/
class Solution1449Test {

    @Test
    void testLargestNumber1() {
        // Arrange
        int[] cost = {4, 3, 2, 5, 6, 7, 2, 5, 5};
        int target = 12;
        Solution1449 solution = new Solution1449();

        // Act
        String result = solution.largestNumber(cost, target);

        // Assert
        assertEquals("777777", result);
    }
}