package original;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
/**
* Test class of Solution0418.
*/
class Solution0418Test {
    @Test
    void testWordsTyping_SimpleSentence_ReturnsCorrectCount() {
        // Arrange
        String[] sentence = {"hello", "world"};
        int rows = 2;
        int cols = 10;
        Solution0418 solution = new Solution0418();

        // Act
        int actual = solution.wordsTyping(sentence, rows, cols);

        // Assert
        assertEquals(1, actual);
    }
}