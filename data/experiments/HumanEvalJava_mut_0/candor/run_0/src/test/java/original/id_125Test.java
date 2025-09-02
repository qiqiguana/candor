package original;

import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
/**
* Test class of SplitWords.
*/
class SplitWordsTest {
    @Test
    void testSplitWords_ReturnsList_WhenInputContainsSpace() {
        // Arrange
        String input = "Hello World";
        // Act
        Object result = SplitWords.splitWords(input);
        // Assert
        assertNotNull(result);
        assertTrue(result instanceof List);
        assertEquals(2, ((List<?>) result).size());
    }
}
