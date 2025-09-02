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
    
    @Test
        public void testNothing(){
            SplitWords s = new SplitWords();
            }
    @Test
    public void testSplitWordsNoWhitespaceOrCommas() {
        int expected = 3;
        Object result = SplitWords.splitWords("abcdef");
        assertEquals(expected, result);
    }
    @Test
    public void testSplitOnComma() {
        String input = "Hello,world!";
        Object expected = Arrays.asList("Hello", "world!");
        Object result = SplitWords.splitWords(input);
        assertNotEquals(result.getClass(), Integer.class);
        assertEquals(expected, result);
    }
                                    
}