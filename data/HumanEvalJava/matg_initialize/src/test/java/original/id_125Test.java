package original;

import java.util.Arrays;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
* Test class of SplitWords.
*/
class SplitWordsTest {
    @Test
    void testSplitWords_CommaSeparator() {
        Object result = SplitWords.splitWords("Hello,world!");
        assertEquals(Arrays.asList("Hello", "world!"), result);
    }
}
