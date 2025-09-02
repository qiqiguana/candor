package original;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
/**
* Test class of Solution2024.
*/
class Solution2024Test {
    @Test
    void maxConsecutiveAnswers_WhenKIsEqualToOne_ShouldReturnMaxLengthMinusOne() {
        // Arrange
        Solution2024 solution = new Solution2024();
        String answerKey = "TTFTTFT";
        int k = 1;

        // Act
        int result = solution.maxConsecutiveAnswers(answerKey, k);

        // Assert
        assertEquals(5, result);
    }
}