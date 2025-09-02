package original;

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
    void testWordsString_SplitByCommaAndSpace() {
        List<Object> actual = WordsString.wordsString("Hi, my name is John");
        List<Object> expected = Arrays.asList("Hi", "my", "name", "is", "John");
        assertEquals(expected, actual);
    }
    
    @Test
        public void testNothing(){
            WordsString s = new WordsString();
            }
    @Test
    public void test_WordsString_class_creation() {
        // Verify that the WordsString class can be instantiated without any issues.
        assertDoesNotThrow(() -> new original.WordsString());
    }
    @Test
    public void test_wordsString_method_with_empty_string() {
        // Verify that the wordsString method returns an empty list when given an empty string as input.
        List<Object> expected = new ArrayList<>();
        assertEquals(expected, original.WordsString.wordsString(""));
    }
                                    
}