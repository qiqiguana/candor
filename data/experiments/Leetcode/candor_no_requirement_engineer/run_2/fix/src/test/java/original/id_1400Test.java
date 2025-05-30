package original;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
/**
* Test class of Solution1400.
*/
class Solution1400Test {
    @Test
    void canConstruct_returnFalse_WhenStringLengthIsLessThanK() {
        // Arrange
        String s = "abc";
        int k = 4;
        Solution1400 solution1400 = new Solution1400();
        
        // Act and Assert
        assertFalse(solution1400.canConstruct(s, k));
    }
}