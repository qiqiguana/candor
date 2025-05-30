package original;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
/**
* Test class of Solution0670.
*/
class Solution0670Test {

    @Test
    void test_maximumSwap_1() {
        // Arrange
        Solution0670 solution = new Solution0670();
        int num = 2736;
        int expected = 7236;
        
        // Act
        int result = solution.maximumSwap(num);
        
        // Assert
        assertEquals(expected, result);
    }
}