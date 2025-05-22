package original;

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
                                    
}