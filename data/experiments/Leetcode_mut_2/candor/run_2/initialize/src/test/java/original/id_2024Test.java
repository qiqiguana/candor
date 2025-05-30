package original;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
/**
* Test class of Solution2024.
*/
class Solution2024Test {
    @Test
    void testMaxConsecutiveAnswers() {
        Solution2024 solution = new Solution2024();
        String answerKey = "TTFTTFT";
        int k = 1;
        assertEquals(5, solution.maxConsecutiveAnswers(answerKey, k));
    }
}
