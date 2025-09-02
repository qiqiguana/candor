package original;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
/**
* Test class of Solution2047.
*/
class Solution2047Test {
    @Test
    void testCountValidWords_HappyPath() {
        // Arrange
        String sentence = "cat dog! cat hello world";
        int expected = 5;
        Solution2047 solution = new Solution2047();

        // Act
        int actual = solution.countValidWords(sentence);

        // Assert
        assertEquals(expected, actual);
    }
}