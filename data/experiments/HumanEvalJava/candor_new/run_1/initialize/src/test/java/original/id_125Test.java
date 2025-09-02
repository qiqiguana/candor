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
    void testSplitWords_SpaceSeparated() {
        List<String> expected = Arrays.asList("Hello", "world!");
        Object actual = SplitWords.splitWords("Hello world!");
        assertEquals(expected, actual);
    }
}