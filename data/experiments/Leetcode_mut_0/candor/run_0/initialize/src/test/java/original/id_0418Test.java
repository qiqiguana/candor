package original;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
* Test class of Solution0418.
*/
class Solution0418Test {

    @Test
    void testWordsTyping_SimpleSentence_ReturnsExpectedTimes() {
        // Arrange
        Solution0418 solution = new Solution0418();
        String[] sentence = {"hello", "world"};
        int rows = 2;
        int cols = 8;
        int expectedResult = 1;
        
        // Act
        int result = solution.wordsTyping(sentence, rows, cols);
        
        // Assert
        assertEquals(expectedResult, result);
    }
}