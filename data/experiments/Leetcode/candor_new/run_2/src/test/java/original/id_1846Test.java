package original;

import java.util.Arrays;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
/**
* Test class of Solution1846.
*/
class Solution1846Test {
    @Test
    void test_maximumElementAfterDecrementingAndRearranging_SimpleCase_ReturnsExpectedResult() {
        // Arrange
        int[] arr = {2, 2, 1, 2, 1};
        int expectedResult = 2;
        Solution1846 solution = new Solution1846();
        
        // Act
        int actualResult = solution.maximumElementAfterDecrementingAndRearranging(arr);
        
        // Assert
        assertEquals(expectedResult, actualResult);
    }
}