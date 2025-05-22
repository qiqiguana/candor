package oracle1;

import java.util.ArrayList;

import java.util.List;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
/**
* Test class of SelectWords.
*/
class SelectWordsTest {
    @Test
    void testSelectWords_EmptyString_ReturnsEmptyList() {
        List<Object> result = SelectWords.selectWords("", 4);
        assertTrue(result.isEmpty());
    }
    
    @Test
        void testNothing(){
            SelectWords s = new SelectWords();
            }
    @Test
    public void testSelectWordsEmptyString() {
        List<Object> result = SelectWords.selectWords("", 4);
        assertTrue(result.isEmpty());
    }
    @Test
    public void testSelectWordsSingleWord() {
        List<Object> result = SelectWords.selectWords("hello", 3);
        assertEquals(1, result.size());
        assertEquals("hello", result.get(0));
    }
    @Test
    public void testSelectWordsNullInput() {
        assertThrows(NullPointerException.class, () -> SelectWords.selectWords(null, 4));
    }
    @Test
    public void testSelectWordsMultipleWordsDifferentConsonants1() {
        List<Object> result = SelectWords.selectWords("hello world abc", 4);
        assertEquals(1, result.size());
        assertEquals("world", result.get(0));
    }
                                    
}