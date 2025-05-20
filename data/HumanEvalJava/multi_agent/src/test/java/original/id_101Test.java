package original;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
/**
* Test class of WordsString.
*/
class WordsStringTest {
    @Test
    void testWordsString2() {
        List<Object> expected = new ArrayList<>(Arrays.asList("Hi", "my", "name", "is", "John"));
        List<Object> actual = WordsString.wordsString("Hi, my name is John");
        assertEquals(expected,actual);
    }
}
