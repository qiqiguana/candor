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
    void testSplitWords_withComma_separateIntoWords() {
        List<String> expected = Arrays.asList("Hello", "world!");
        Object actual = SplitWords.splitWords("Hello,world!");
        assertEquals(expected, actual);
    }
    
    @Test
     void testNothing(){
         SplitWords s = new SplitWords();
         }
    @Test
    public void testSplitOnWhitespace() {
        Object result = SplitWords.splitWords("Hello world!");
        assertEquals(Arrays.asList("Hello", "world!"), result);
    }
    @Test
    public void testSplitOnComma() {
        Object result = SplitWords.splitWords("Hello,world!");
        assertEquals(Arrays.asList("Hello", "world!"), result);
    }
    @Test
    public void testNoWhitespaceOrComma() {
        Object result = SplitWords.splitWords("abcdef");
        assertEquals(3, result);
    }
    @Test
    public void testMixedCaseString() {
        Object result = SplitWords.splitWords("aaaBb");
        assertEquals(1, result);
    }
    @Test
    public void testEmptyString() {
        Object result = SplitWords.splitWords("");
        assertEquals(0, result);
    }
    @Test
    public void testWhitespaceOnlyStringFixed() {
        Object result = SplitWords.splitWords(" ");
        assertEquals(0, ((List<?>)result).size());
    }
                                  
}