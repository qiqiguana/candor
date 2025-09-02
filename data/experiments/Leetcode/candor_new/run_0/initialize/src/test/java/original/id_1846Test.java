package original;

import java.util.Arrays;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
/**
* Test class of Solution1846.
*/
class Solution1846Test {
    @Test
    void test_maximumElementAfterDecrementingAndRearranging_SimpleCase() {
        // Arrange
        int[] arr = {2, 2, 1, 2, 1};
        int expectedValue = 2;
        Solution1846 instanceUnderTest = new Solution1846();

        // Act
        int actualValue = instanceUnderTest.maximumElementAfterDecrementingAndRearranging(arr);

        // Assert
        assertEquals(expectedValue, actualValue);
    }
}