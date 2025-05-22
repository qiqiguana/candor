package original;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
/**
* Test class of StringSequence.
*/
class StringSequenceTest {
    @Test
    void testStringSequenceZero() {
        // Arrange and Act
        String result = StringSequence.stringSequence(0);
        // Assert
        assertEquals("0", result);
    }
}