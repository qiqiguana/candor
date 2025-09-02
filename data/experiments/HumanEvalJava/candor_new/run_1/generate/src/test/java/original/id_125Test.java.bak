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
    
    @Test
        public void testNothing(){
            SplitWords s = new SplitWords();
            }
    @Test
    public void test_Split_on_comma() {
        Object result = SplitWords.splitWords("Hello,world!");
        List<Object> expected = Arrays.asList("Hello", "world!");
        assertEquals(expected, result);
    }
    @Test
    public void test_Count_lower_case_letters() {
        Object result = SplitWords.splitWords("abcdef");
        assertEquals(3, result);
    }
    @Test
    public void testOddCountingWithNoLowercaseLetters() {
        Object result = SplitWords.splitWords("ABCDEF");
        assertEquals(0, result);
    }
                                    
}