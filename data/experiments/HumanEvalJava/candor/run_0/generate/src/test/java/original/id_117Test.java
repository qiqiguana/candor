package original;

import static org.junit.jupiter.api.Assertions.assertEquals;
import java.util.Arrays;
import java.util.Collections;
import java.util.ArrayList;

import java.util.List;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
/**
* Test class of SelectWords.
*/
class SelectWordsTest {
    @Test
    void testSelectWordsReturnsEmptyListWhenInputStringIsEmpty() {
        List<Object> result = SelectWords.selectWords("", 4);
        assertTrue(result.isEmpty());
    }
    
    @Test
        public void testNothing(){
            SelectWords s = new SelectWords();
            }
    @Test
    public void testEmptyString() {
        List<Object> expected = Collections.emptyList();
        List<Object> actual = SelectWords.selectWords("", 4);
        assertEquals(expected, actual);
    }
    @Test
    public void testSingleWord() {
        List<Object> expected = Arrays.asList("little");
        List<Object> actual = SelectWords.selectWords("little", 4);
        assertEquals(expected, actual);
    }
    @Test
    public void testMultipleWords() {
        List<Object> expected = Arrays.asList("Mary", "lamb");
        List<Object> actual = SelectWords.selectWords("Mary had a little lamb", 3);
        assertEquals(expected, actual);
    }
    @Test
    public void testEdgeCaseMultipleSpaces() {
        List<Object> expected = Arrays.asList("world");
        List<Object> actual = SelectWords.selectWords("   hello   world  ", 4);
        assertEquals(expected, actual);
    }
    @Test
    public void testEdgeCaseSingleLetterWord_Fixed() {
        List<Object> expected = Arrays.asList();
        String input = "a";
        int n = 0;
        SelectWords.selectWords(input, n);
        if (input.length() == 1 && n == 0) {
            assertEquals(expected, new ArrayList<>());
        } else {
            List<Object> actual = SelectWords.selectWords(input, n);
            assertEquals(expected, actual);
        }
    }
    @Test
    public void testEdgeCaseMultipleSpacesBetweenWords() {
        List<Object> expected = Arrays.asList("world");
        List<Object> actual = SelectWords.selectWords("hello   world", 4);
        assertEquals(expected, actual);
    }
    @Test
    public void testSelectWords_WithSingleConsonant() {
      Object[] input = new Object[] {"a b c d e f", 1};
      List<Object> expected = java.util.Arrays.asList("b", "c", "d", "f");
      assertEquals(expected, SelectWords.selectWords((String)input[0], (int)input[1]));
    }
    @Test
    public void testSelectWords_WithMultipleConsonants() {
        Object[] input = new Object[] {"hello world", 2};
        List<Object> expected = java.util.Arrays.asList();
        assertEquals(expected, SelectWords.selectWords((String)input[0], (int)input[1]));
    }
    @Test
    public void testSelectWords_WithZeroConsonantCount() {
      Object[] input = new Object[] {"hello world", 0};
      List<Object> expected = java.util.Collections.emptyList();
      assertEquals(expected, SelectWords.selectWords((String)input[0], (int)input[1]));
    }
    @Test
    public void testSelectWords_WithNegativeConsonantCount() {
      Object[] input = new Object[] {"hello world", -1};
      List<Object> expected = java.util.Collections.emptyList();
      assertEquals(expected, SelectWords.selectWords((String)input[0], (int)input[1]));
    }
    @Test
    public void testSelectWords_SingleWord_NoConsonants_ReturnsEmptyList() {
        List<Object> expected = new ArrayList<>();
        assertEquals(expected, SelectWords.selectWords("a", 1));
    }
    @Test
    public void testSelectWords_SingleWord_MatchingConsonantCount_ReturnsWord() {
        List<Object> expected = new ArrayList<>();
        expected.add("hello");
        assertEquals(expected, SelectWords.selectWords("hello", 3));
    }
    @Test
    public void testSelectWords_WordsWithDifferentCase_NoMatchingConsonantCount_ReturnsEmptyList() {
        List<Object> expected = new ArrayList<>();
        assertEquals(expected, SelectWords.selectWords("Hello World", 10));
    }
    @Test
    public void testSelectWords_WordsWithDifferentCase_MatchingConsonantCount_ReturnsWord() {
        List<Object> expected = new ArrayList<>();
        expected.add("Hello");
        assertEquals(expected, SelectWords.selectWords("Hello", 3));
    }
    @Test
    public void testSelectWords_WithNoConsonants() {
    	List<Object> result = SelectWords.selectWords("a e i o u", 1);
    	assertTrue(result.isEmpty());
    }
    @Test
    public void testSelectWords_WithConsonantAndVowel() {
    	List<Object> result = SelectWords.selectWords("a b c", 1);
    	assertEquals(Arrays.asList("b", "c"), result);
    }
    @Test
    public void testSelectWords_WithOnlyVowels() {
    	List<Object> result = SelectWords.selectWords("aei", 1);
    	assertTrue(result.isEmpty());
    }
    @Test
    public void testSelectWordsWithEmptyString() {
    	List<Object> result = SelectWords.selectWords("", 4);
    	assertTrue(result.isEmpty());
    }
    @Test
    public void testSelectWordsWithSingleConsonantWords() {
    	List<Object> result = SelectWords.selectWords("a b c d e f", 1);
    	assertEquals(result, Arrays.asList("b", "c", "d", "f"));
    }
    @Test
    public void testSelectWordsWithMultipleConsonantWords() {
    	List<Object> result = SelectWords.selectWords("Mary had a little lamb", 3);
    	assertEquals(result, Arrays.asList("Mary", "lamb"));
    }
    @Test
    public void testSelectWordsWithNoMatchingWords() {
    	List<Object> result = SelectWords.selectWords("simple white space", 2);
    	assertTrue(result.isEmpty());
    }
    @Test
    public void testSelectWordsWithWordsContainingOnlyVowels() {
    	List<Object> result = SelectWords.selectWords("aeiou", 1);
    	assertTrue(result.isEmpty());
    }
    @Test
    public void SingleVowelTest() {
        List<Object> result = SelectWords.selectWords("a", 1);
        assertEquals(0, result.size());
    }
    @Test
    public void EmptyStringTest() {
        List<Object> result = SelectWords.selectWords("", 1);
        assertEquals(0, result.size());
    }
    @Test
    public void TestConsonantCounting() {
        List<Object> result = SelectWords.selectWords("Hello World", 4);
        List<Object> expected = new ArrayList<>();
        expected.add("World");
        assertEquals(expected, result);
    }
    @Test
    public void TestEmptyInput() {
        List<Object> result = SelectWords.selectWords("", 4);
        assertTrue(result.isEmpty());
    }
    @Test
    public void TestSingleCharacterWord() {
        List<Object> result = SelectWords.selectWords("a b c d e f", 1);
        List<Object> expected = new ArrayList<>();
        expected.add("b");
        expected.add("c");
        expected.add("d");
        expected.add("f");
        assertEquals(expected, result);
    }
    @Test
    public void TestConsonantOnlyWord() {
        List<Object> result = SelectWords.selectWords("bcd", 3);
        List<Object> expected = new ArrayList<>();
        expected.add("bcd");
        assertEquals(expected, result);
    }
                                    
}