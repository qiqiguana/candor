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
    void testSplitWords_SingleWord_ReturnsNumberOfLowercaseLettersWithOddOrder() {
        Object result = SplitWords.splitWords("abcdef");
        assertEquals(3, result);
    }
    @Test
    public void testSplitOnWhitespace() {
        Object result = SplitWords.splitWords("Hello world!");
        assertEquals(Arrays.asList("Hello", "world!"), result);
    }
    @Test
    public void testSplitOnCommas() {
        Object result = SplitWords.splitWords("Hello,world!");
        assertEquals(Arrays.asList("Hello", "world!"), result);
    }
    @Test
    public void testNoWhitespaceOrCommas() {
        Object result = SplitWords.splitWords("abcdef");
        assertEquals(3, result);
    }
    @Test
    public void testEmptyString() {
        Object result = SplitWords.splitWords("");
        assertEquals(0, result);
    }
    @Test
    public void testNullInput() {
        assertThrows(NullPointerException.class, () -> SplitWords.splitWords(null));
    }
    @Test
    public void testWhitespaceOnly() {
        Object result = SplitWords.splitWords(" ");
        assertEquals(Arrays.asList(), result);
    }
    @Test
    public void testCommasOnly() {
        Object result = SplitWords.splitWords(",");
        assertEquals(Arrays.asList(), result);
    }
    @Test
    public void test_split_words_with_whitespace() {
        Object result = SplitWords.splitWords("Hello world!");
        List<Object> expected = Arrays.asList("Hello", "world!");
        assertEquals(expected, result);
    }
    @Test
    public void test_split_words_with_comma() {
        Object result = SplitWords.splitWords("Hello,world!");
        List<Object> expected = Arrays.asList("Hello", "world!");
        assertEquals(expected, result);
    }
    @Test
    public void test_split_words_without_whitespace_or_comma() {
        Object result = SplitWords.splitWords("abcdef");
        int expected = 3;
        assertEquals(expected, result);
    }
    @Test
    public void test_split_words_with_empty_string() {
        Object result = SplitWords.splitWords("");
        int expected = 0;
        assertEquals(expected, result);
    }
    @Test
    public void test_split_words_with_lower_case_letters_only() {
        Object result = SplitWords.splitWords("aaabb");
        int expected = 2;
        assertEquals(expected, result);
    }
    @Test
    public void test_split_words_with_upper_case_letters_only() {
        Object result = SplitWords.splitWords("AAAABB");
        int expected = 0;
        assertEquals(expected, result);
    }
    @Test
    public void test_Split_on_whitespace() {
        Object[] expected = {"Hello", "world!"};
        Object result = SplitWords.splitWords("Hello world!");
        assertArrayEquals(expected, ((List<?>)result).toArray());
    }
    @Test
    public void test_Split_on_comma() {
        Object[] expected = {"Hello", "world!"};
        Object result = SplitWords.splitWords("Hello,world!");
        assertArrayEquals(expected, ((List<?>)result).toArray());
    }
    @Test
    public void test_No_whitespace_or_comma() {
        int expected = 3;
        Object result = SplitWords.splitWords("abcdef");
        assertEquals(expected, result);
    }
    @Test
    public void test_Empty_string() {
        Object result = SplitWords.splitWords("");
        assertTrue(result instanceof Integer);
        assertEquals(0, (int) result);
    }
    @Test
    public void test_Multiple_characters() {
        int expected = 2;
        Object result = SplitWords.splitWords("aaabb");
        assertEquals(expected, result);
    }
}