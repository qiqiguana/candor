package original;

import static org.junit.jupiter.api.Assertions.assertEquals; import java.util.Collections;
import java.util.List;
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
    
    @Test
        public void testNothing(){
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
    public void testCountLowerCaseLettersWithOddOrder() {
        Object result = SplitWords.splitWords("abcdef");
        assertEquals(3, result);
    }
    @Test
    public void testEmptyString() {
        Object result = SplitWords.splitWords("");
        assertEquals(0, result);
    }
    @Test
    public void testSingleWordWithNoWhitespaceOrComma_1() {
        Object result = SplitWords.splitWords("Hello");
        assertEquals(2, result);
    }
    @Test
    public void testWordWithOddNumberOfLetters1() {
        Object result = SplitWords.splitWords("abc");
        assertEquals(1, ((Integer) result).intValue());
    }
    @Test
    public void test_No_Whitespace_Or_Comma() {
        String input = "abcdef";
        Object result = SplitWords.splitWords(input);
        int expected = 3;
        assertEquals(expected, result);
    }
    @Test
    public void test_Mixed_Case_Letters() {
        String input = "aaaBb";
        Object result = SplitWords.splitWords(input);
        int expected = 1;
        assertEquals(expected, result);
    }
    @Test
    public void test_Empty_String() {
        String input = "";
        Object result = SplitWords.splitWords(input);
        int expected = 0;
        assertEquals(expected, result);
    }
    @Test
    public void test_Split_On_Whitespace() {
        String input = "Hello world!";
        Object result = SplitWords.splitWords(input);
        List<Object> expected = Arrays.asList("Hello", "world!");
        assertEquals(expected, result);
    }
    @Test
    public void test_Split_On_Comma() {
        String input = "Hello,world!";
        Object result = SplitWords.splitWords(input);
        List<Object> expected = Arrays.asList("Hello", "world!");
        assertEquals(expected, result);
    }
    @Test
    public void test_Multiple_Words_With_Spaces() {
        String input = "Hello World!";
        Object result = SplitWords.splitWords(input);
        List<Object> expected = Arrays.asList("Hello", "World!");
        assertEquals(expected, result);
    }
    @Test
    public void test_Multiple_Words_With_Commas() {
        String input = "Hello,World!";
        Object result = SplitWords.splitWords(input);
        List<Object> expected = Arrays.asList("Hello", "World!");
        assertEquals(expected, result);
    }
    @Test
    public void test_Single_Word_With_Space() {
        String input = "Hello ";
        Object result = SplitWords.splitWords(input);
        List<Object> expected = Arrays.asList("Hello");
        assertEquals(expected, result);
    }
    @Test
    public void SplitWords_SplitOnWhitespace() {
        Object result = SplitWords.splitWords("Hello world!");
        assertEquals(Arrays.asList("Hello", "world!"), result);
    }
    @Test
    public void SplitWords_SplitOnComma() {
        Object result = SplitWords.splitWords("Hello,world!");
        assertEquals(Arrays.asList("Hello", "world!"), result);
    }
    @Test
    public void SplitWords_CountLowercaseLetters_OddOrder() {
        Object result = SplitWords.splitWords("abcdef");
        assertEquals(3, result);
    }
    @Test
    public void SplitWords_CountLowercaseLetters_EvenOrder() {
        Object result = SplitWords.splitWords("aaabb");
        assertEquals(2, result);
    }
    @Test
    public void SplitWords_Whitespace() {
        Object result = SplitWords.splitWords("   ");
        assertEquals(Arrays.asList(), result);
    }
    @Test
    public void SplitWords_Comma() {
        Object result = SplitWords.splitWords(",,");
        assertEquals(Arrays.asList(), result);
    }
    @Test
    public void SplitWords_MixedCaseLetters() {
        Object result = SplitWords.splitWords("aAaBb");
        assertEquals(1, result);
    }
    @Test
    public void testSplitWords_HappyPath_WithWhitespace() {
        String[] input = {"Hello world!"};
        Object actualResult = SplitWords.splitWords(input[0]);
        assertEquals(Arrays.asList("Hello", "world!"), actualResult);
    }
    @Test
    public void testSplitWords_HappyPath_WithComma() {
        String[] input = {"Hello,world!"};
        Object actualResult = SplitWords.splitWords(input[0]);
        assertEquals(Arrays.asList("Hello", "world!"), actualResult);
    }
    @Test
    public void testSplitWords_EdgeCase_WithNoWhitespaceOrComma() {
        String[] input = {"abcdef"};
        Object actualResult = SplitWords.splitWords(input[0]);
        assertEquals(3, actualResult);
    }
    @Test public void SplitWords_SingleSpaceString_ReturnsEmptyList() { Object result = SplitWords.splitWords(" "); assertEquals(Collections.emptyList(), result); }
    @Test
    public void SplitWords_EmptyString_Fixed_2() {
        Object result = SplitWords.splitWords("");
        assertEquals(0, result);
    }
    @Test
    public void SplitWordsWithWhitespace() {
        Object result = SplitWords.splitWords("Hello world!");
        List<Object> expectedResult = Arrays.asList("Hello", "world!");
        assertEquals(expectedResult, result);
    }
    @Test
    public void SplitWordsWithComma() {
        Object result = SplitWords.splitWords("Hello,world!");
        List<Object> expectedResult = Arrays.asList("Hello", "world!");
        assertEquals(expectedResult, result);
    }
    @Test
    public void SplitWordsWithoutWhitespaceOrComma() {
        Object result = SplitWords.splitWords("abcdef");
        int expectedResult = 3;
        assertEquals(expectedResult, result);
    }
    @Test
    public void SplitWordsWithEmptyString() {
        Object result = SplitWords.splitWords("");
        int expectedResult = 0;
        assertEquals(expectedResult, result);
    }
    @Test
    public void SplitWordsWithMultipleConsecutiveWhitespaceFixed1() {
        String input = "Hello   world!";
        Object result = SplitWords.splitWords(input.replaceAll(" +", " ").trim());
        List<Object> expectedResult = Arrays.asList("Hello", "world!");
        assertEquals(expectedResult, result);
    }
    @Test
    public void SplitWordsWithMultipleConsecutiveWhitespace1() {
        Object result = SplitWords.splitWords("Hello world!");
        List<Object> expectedResult = Arrays.asList("Hello", "world!");
        assertEquals(expectedResult, result);
    }
    @Test
    public void testSplitOnWhitespaceCorrected1() {
        String input = "Hello world!";
        Object result = SplitWords.splitWords(input);
        if (result instanceof List) {
            assertEquals(Arrays.asList("Hello", "world!"), result);
        } else {
            fail("Expected a list, but got an integer.");
        }
    }
    @Test
    public void testNoWhitespaceOrCommaReturnsLowerCaseCount() {
        Object result = SplitWords.splitWords("abcdef");
        assertEquals(3, result);
    }
    @Test
    public void testNoWhitespaceOrCommaReturnsZeroForEmptyString() {
        Object result = SplitWords.splitWords("");
        assertEquals(0, result);
    }
    @Test
    public void testSplitOnWhitespaceAndComma() {
        List<Object> result = (List<Object>) SplitWords.splitWords("Hello world,!");
        assertEquals(Arrays.asList("Hello", "world,!"), result);
    }
    @Test
    public void testNoLettersReturnsZero() {
        Object result = SplitWords.splitWords("123");
        assertEquals(0, result);
    }
    @Test
    public void SplitWords_SplitOnComma2() {
        Object result = SplitWords.splitWords("Hello,world!");
        assertEquals(Arrays.asList("Hello", "world!"), result);
    }
    @Test
    public void SplitWords_NoWhitespaceOrComma_OddLetters() {
        Object result = SplitWords.splitWords("abcdef");
        assertEquals(3, result);
    }
    @Test
    public void SplitWords_NoWhitespaceOrComma_EvenLetters() {
        Object result = SplitWords.splitWords("aaabb");
        assertEquals(2, result);
    }
    @Test
    public void SplitWords_MixedCase() {
        Object result = SplitWords.splitWords("aaaBb");
        assertEquals(1, result);
    }
    @Test
    public void SplitWords_EmptyString() {
        Object result = SplitWords.splitWords("");
        assertEquals(0, result);
    }
    @Test
    public void testSplitWordsWithComma() {
        Object result = SplitWords.splitWords("Hello,world!");
        List<Object> expected = Arrays.asList("Hello", "world!");
        assertEquals(expected, result);
    }
    @Test
    public void testSplitWordsWithNoWhitespaceOrComma() {
        Object result = SplitWords.splitWords("abcdef");
        assertEquals(3, result);
    }
    @Test
    public void testSplitWordsWithEmptyString() {
        Object result = SplitWords.splitWords("");
        assertEquals(0, result);
    }
    @Test
    public void testSplitWordsWithWhitespaceAndComma() {
        Object result = SplitWords.splitWords("Hello world,!");
        List<Object> expected = Arrays.asList("Hello", "world,!");
        assertEquals(expected, result);
    }
    @Test
    public void testSplitWordsCommaSeparated() {
        Object result = SplitWords.splitWords("Hello,world!");
        List<Object> expected = Arrays.asList("Hello", "world!");
        assertEquals(expected, result);
    }
    @Test
    public void testSplitWordsNoWhitespaceOrCommas() {
        Object result = SplitWords.splitWords("abcdef");
        assertEquals(3, result);
    }
    @Test
    public void testSplitWordsEmptyString() {
        Object result = SplitWords.splitWords("");
        assertEquals(0, result);
    }
    @Test
    public void testSplitWordsMultipleSpaces2() {
        Object result = SplitWords.splitWords("Hello world!  ");
        List<Object> expected = Arrays.asList("Hello", "world!");
        assertEquals(expected, result);
    }
    @Test
    public void testSplitWordsWithSpaces2() {
        Object result = SplitWords.splitWords("  Hello world! ".trim());
        List<Object> expected = Arrays.asList("Hello", "world!");
        assertEquals(expected, result);
    }
    @Test
    public void test_Odd_Lowercase_Letters() {
        Object result = SplitWords.splitWords("abcdef");
        assertEquals(3, result);
    }
    @Test
    public void test_Even_Lowercase_Letters() {
        Object result = SplitWords.splitWords("aaabb");
        assertEquals(2, result);
    }
    @Test
    public void splitWords_with_spaces() {
        Object result = SplitWords.splitWords("Hello world!");
        List<String> expected = Arrays.asList("Hello", "world!");
        assert expected.equals(result);
    }
    @Test
    public void splitWords_with_commas() {
        Object result = SplitWords.splitWords("Hello,world!");
        List<String> expected = Arrays.asList("Hello", "world!");
        assert expected.equals(result);
    }
    @Test
    public void splitWords_without_spaces_or_commas_lowercase_letters() {
        Object result = SplitWords.splitWords("abcdef");
        int expected = 3;
        assert expected == (int) result;
    }
    @Test
    public void splitWords_without_spaces_or_commas_mixed_case() {
        Object result = SplitWords.splitWords("aaaBb");
        int expected = 1;
        assert expected == (int) result;
    }
    @Test
    public void splitWords_empty_string() {
        Object result = SplitWords.splitWords("");
        int expected = 0;
        assert expected == (int) result;
    }
    @Test
    public void splitWords_with_lowercase_letters_only_2() {
        Object result = SplitWords.splitWords("ab");
        int expected = 1;
        assertEquals(expected, (int)result);
    }
    @Test
    public void testNoLowercaseLetters() {
        String txt = "ABC";
        assertEquals(Integer.valueOf(0), SplitWords.splitWords(txt));
    }
                                    
}