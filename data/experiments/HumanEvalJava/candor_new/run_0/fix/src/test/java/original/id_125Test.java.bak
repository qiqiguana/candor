package original;

import java.util.Arrays;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
* Test class of SplitWords.
*/
class SplitWordsTest {

    @Test
    void testSplitWords_WithSpace_ReturnsListOfWords() {
        Object result = SplitWords.splitWords("Hello world!");
        assertEquals(Arrays.asList("Hello", "world!"), result);
    }
    
    @Test
        public void testNothing(){
            SplitWords s = new SplitWords();
            }
    @Test
    public void testNoWhitespaceOrComma() {
    	Object result = SplitWords.splitWords("abcdef");
    	assertEquals(3, result);
    }
    @Test
    public void testMultipleLowercaseLettersFixed4() {
        Object result = SplitWords.splitWords("aaabbB");
        assertTrue(result instanceof Integer);
        assertEquals(2, (int)result);
    }
    @Test
    public void SplitWordsWithComma() {
        Object result = SplitWords.splitWords("Hello,world!");
        assertEquals(Arrays.asList("Hello", "world!"), result);
    }
                                    

}