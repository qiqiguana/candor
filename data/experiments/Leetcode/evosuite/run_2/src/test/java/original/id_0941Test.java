package original;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
/**
* Test class of Solution0941.
*/
class Solution0941Test {
    @Test
    void validMountainArray_EmptyArray_ReturnsFalse() {
        // Arrange
        int[] arr = {};
        Solution0941 solution = new Solution0941();
        
        // Act
        boolean result = solution.validMountainArray(arr);
        
        // Assert
        assertFalse(result);
    }
}