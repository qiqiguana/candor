package original;

import java.util.List;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import java.util.ArrayList;
/**
* Test class of Longest.
*/
class LongestTest {
    @Test
    void testLongest_ReturnsFirstString_WhenMultipleStringsOfSameLength() {
        // Arrange
        List<Object> input = new ArrayList<>();
        input.add("abc");
        input.add("xyz");
        input.add("def");
        String expectedResult = "abc";

        // Act
        String result = Longest.longest(input);

        // Assert
        assertEquals(expectedResult, result);
    }
}