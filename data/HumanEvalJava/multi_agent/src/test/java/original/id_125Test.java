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
    void testSplitWords_WhenGivenStringWithSpaces_ReturnsListOfWords() {
        String txt = "Hello world!";
        List<String> expected = Arrays.asList("Hello", "world!");
        assertEquals(expected, SplitWords.splitWords(txt));
    }
}
