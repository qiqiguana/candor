package original;

import java.util.ArrayList;

import java.util.List;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
/**
* Test class of ParseNestedParens.
*/
class ParseNestedParensTest {
    @Test
    void testParseNestedParens_SingleGroup_ReturnsCorrectDepth() {
        // Arrange
        String input = "(()())";
        int expected = 2;

        // Act
        List<Integer> result = ParseNestedParens.parseNestedParens(input);

        // Assert
        assertEquals(expected, result.get(0));
    }
}
