package original;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
/**
* Test class of Solution3467.
*/
class Solution3467Test {
    @Test
    void transformArray_singleElementArray_returnArrayOfSameSize() {
        // Arrange
        int[] input = {1};
        int[] expectedOutput = {1};
        Solution3467 solution = new Solution3467();

        // Act
        int[] actualOutput = solution.transformArray(input);

        // Assert
        assertArrayEquals(expectedOutput, actualOutput);
    }
}