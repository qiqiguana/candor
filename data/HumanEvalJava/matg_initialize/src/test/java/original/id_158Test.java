package original;

import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
/**
* Test class of FindMax.
*/
class FindMaxTest {
    @Test
    void testFindMax_WithDuplicateWords_ReturnsFirstWordInLexicographicalOrder() {
        List<String> words = new ArrayList<>();
        words.add("name");
        words.add("enam");
        words.add("game");
        String expected = "enam";
        String actual = FindMax.findMax(words);
        assertEquals(expected, actual);
    }
}