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
    void testSelectWords_WithMultipleWords_ReturnsWordsWithExactConsonants() {
        // Arrange
        String input = "Mary had a little lamb";
        int consonantCount = 3;
        List<Object> expected = new ArrayList<>();
        expected.add("Mary");
        expected.add("lamb");

        // Act
        List<Object> actual = SelectWords.selectWords(input, consonantCount);

        // Assert
        assertEquals(expected, actual);
    }
}