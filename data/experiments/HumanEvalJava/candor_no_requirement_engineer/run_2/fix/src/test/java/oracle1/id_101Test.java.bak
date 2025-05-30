package oracle1;

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
    void testWordsStringWithEmptyString() {
        List<Object> result = WordsString.wordsString("");
        assertTrue(result.isEmpty());
    }
    
    @Test
        void testNothing(){
            WordsString s = new WordsString();
            }
    @Test
    public void TestEmptyString() {
        List<Object> result = WordsString.wordsString("");
        assertTrue(result.isEmpty());
    }
    @Test
    public void TestSingleWord() {
        List<Object> result = WordsString.wordsString("hello");
        assertEquals(1, result.size());
        assertEquals("hello", result.get(0));
    }
    @Test
    public void TestMultipleWordsCommaSeparated() {
        List<Object> result = WordsString.wordsString("one,two,three");
        assertEquals(3, result.size());
        assertEquals("one", result.get(0));
        assertEquals("two", result.get(1));
        assertEquals("three", result.get(2));
    }
    @Test
    public void TestMultipleWordsSpaceSeparated() {
        List<Object> result = WordsString.wordsString("one two three");
        assertEquals(3, result.size());
        assertEquals("one", result.get(0));
        assertEquals("two", result.get(1));
        assertEquals("three", result.get(2));
    }
    @Test
    public void TestMixedSeparators() {
        List<Object> result = WordsString.wordsString("one ,two, three");
        assertEquals(3, result.size());
        assertEquals("one", result.get(0));
        assertEquals("two", result.get(1));
        assertEquals("three", result.get(2));
    }
    @Test
    public void TestMultipleWordsWithCommasAndSpaces() {
        List<Object> result = WordsString.wordsString("one, two , three");
        assertEquals(3, result.size());
        assertEquals("one", result.get(0));
        assertEquals("two", result.get(1));
        assertEquals("three", result.get(2));
    }
                                    
}