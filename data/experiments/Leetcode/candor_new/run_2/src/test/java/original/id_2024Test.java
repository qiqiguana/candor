package original;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
/**
* Test class of Solution2024.
*/
class Solution2024Test {
    @Test
    void maxConsecutiveAnswers_False() {
        Solution2024 solution = new Solution2024();
        String answerKey = "TTFF";
        int k = 1;
        assertEquals(3, solution.maxConsecutiveAnswers(answerKey, k));
    }
}