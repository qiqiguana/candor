package original;

import java.util.Arrays;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
/**
* Test class of SplitWords.
*/
class SplitWordsTest {
    @Test
    void testSplitOnWhitespace() {
        String[] expected = {"Hello", "world!"};
        Object actual = SplitWords.splitWords("Hello world!");
        assertTrue(actual instanceof java.util.List);
        assertEquals(Arrays.asList(expected), actual);
    }
}