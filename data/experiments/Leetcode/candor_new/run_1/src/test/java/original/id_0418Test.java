package original;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
/**
* Test class of Solution0418.
*/
class Solution0418Test {
    @Test
    void testWordsTyping() {
        // arrange
        Solution0418 solution = new Solution0418();
        String[] sentence = {"hello", "world"};
        int rows = 2;
        int cols = 8;
        int expected = 1;

        // act
        int actual = solution.wordsTyping(sentence, rows, cols);

        // assert
        assertEquals(expected, actual);
    }
}