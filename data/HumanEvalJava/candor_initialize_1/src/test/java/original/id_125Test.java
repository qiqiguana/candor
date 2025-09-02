package original;

import java.util.Arrays;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import java.util.List;

/**
* Test class of SplitWords.
*/
class SplitWordsTest {

    @Test
    void testSplitWords_SplitOnWhitespace() {
        List<String> expected = Arrays.asList("Hello", "world!");
        Object actual = SplitWords.splitWords("Hello world!");
        assertIterableEquals(expected, (List<String>)actual);
    }
    @Test
    public void testSplitWordsWithSpace() {
        Object result = SplitWords.splitWords("Hello world!");
        assertEquals(Arrays.asList("Hello", "world!"), result);
    }
    @Test
    public void testSplitWordsWithComma() {
        Object result = SplitWords.splitWords("Hello,world!");
        assertEquals(Arrays.asList("Hello", "world!"), result);
    }
    @Test
    public void testSplitWordsWithoutSpaceOrComma() {
        Object result = SplitWords.splitWords("abcdef");
        assertEquals(3, result);
    }
    @Test
    public void testEmptyString() {
        Object result = SplitWords.splitWords("");
        assertTrue(result instanceof Integer);
        assertEquals(0, (int)result);
    }
    @Test
    public void testLeadingAndTrailingSpacesTrimmed() {
        String input = " Hello world! ";
        Object result = SplitWords.splitWords(input.trim());
        assertEquals(Arrays.asList("Hello", "world!"), result);
    }
    @Test
    public void SplitWords_HappyPath_Whitespace() {
        Object result = SplitWords.splitWords("Hello world!");
        List<Object> expectedResult = Arrays.asList("Hello", "world!");
        assertEquals(expectedResult, result);
    }
    @Test
    public void SplitWords_HappyPath_Comma() {
        Object result = SplitWords.splitWords("Hello,world!");
        List<Object> expectedResult = Arrays.asList("Hello", "world!");
        assertEquals(expectedResult, result);
    }
    @Test
    public void SplitWords_EdgeCase_NoWhitespaceOrComma() {
        Object result = SplitWords.splitWords("abcdef");
        int expectedResult = 3;
        assertEquals(expectedResult, result);
    }
    @Test
    public void SplitWords_NegativeTest_NullInput() {
        assertThrows(NullPointerException.class, () -> SplitWords.splitWords(null));
    }
    @Test
    public void SplitWords_EdgeCase_OnlyWhitespace() {
        Object result = SplitWords.splitWords("   ");
        List<Object> expectedResult = Arrays.asList();
        assertEquals(expectedResult, result);
    }
    @Test
    public void SplitWords_EdgeCase_EmptyString() {
        Object result = SplitWords.splitWords(" ");
        List<Object> expectedResult = Arrays.asList();
        assertEquals(expectedResult, result);
    }
    @Test
    public void SplitWords_MultipleCommas() {
        Object result = SplitWords.splitWords("Hello,World!,This,is,a,test");
        List<Object> expectedResult = Arrays.asList("Hello", "World!", "This", "is", "a", "test");
        assertEquals(expectedResult, result);
    }
    @Test
    public void testSplitWordsWithWhitespace() {
        Object result = SplitWords.splitWords("Hello world!");
        List<String> expectedResult = Arrays.asList("Hello", "world!");
        assertEquals(expectedResult, result);
    }

}