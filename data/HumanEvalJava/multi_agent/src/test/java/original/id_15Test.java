package original;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
/**
* Test class of StringSequence.
*/
class StringSequenceTest {
    @Test
    void testStringSequence_ReturnsExpectedString() {
        // Arrange
        int n = 5;
        String expected = "0 1 2 3 4 5";

        // Act
        String actual = StringSequence.stringSequence(n);

        // Assert
        assertEquals(expected, actual);
    }
}