package original;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
/**
* Test class of Solution0748.
*/
class Solution0748Test {
    @Test
    void testShortestCompletingWord() {
        Solution0748 solution = new Solution0748();
        String licensePlate = "1s3 PSt";
        String[] words = {"step", "steps", "stripe", "stepple"};
        assertEquals("steps", solution.shortestCompletingWord(licensePlate, words));
    }
}