package original;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
/**
* Test class of Solution2024.
*/
class Solution2024Test {
    @Test
    void maxConsecutiveAnswers_False_kIsGreaterThanZero_ReturnsExpectedValue() {
        // Arrange
        Solution2024 solution = new Solution2024();
        String answerKey = "TTFTTFT";
        int k = 1;
        int expected = 5;
        
        // Act
        int actual = solution.maxConsecutiveAnswers(answerKey, k);
        
        // Assert
        assertEquals(expected, actual);
    }
}
