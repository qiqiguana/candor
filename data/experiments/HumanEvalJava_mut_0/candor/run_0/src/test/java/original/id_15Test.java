package original;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
/**
* Test class of StringSequence.
*/
class StringSequenceTest {
    @Test
    void testStringSequenceReturnsEmptyStringWhenNIsLessThanZero() {
        // Arrange and Act
        String result = StringSequence.stringSequence(-1);
        // Assert
        assertEquals("", result);
    }
}