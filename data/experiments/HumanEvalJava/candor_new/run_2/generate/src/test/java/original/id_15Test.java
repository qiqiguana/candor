package original;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
/**
* Test class of StringSequence.
*/
class StringSequenceTest {
    @Test
    void testStringSequenceZero() {
        String result = StringSequence.stringSequence(0);
        assertEquals("0", result);
    }
}