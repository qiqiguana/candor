package original;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
/**
* Test class of SelectWords.
*/
class SelectWordsTest {
    @Test
    void testSelectWords_WithEmptyString_ReturnsEmptyList() {
        // Arrange
        String input = "";
        int n = 4;

        // Act
        List<Object> result = SelectWords.selectWords(input, n);

        // Assert
        assertTrue(result.isEmpty());
    }
}