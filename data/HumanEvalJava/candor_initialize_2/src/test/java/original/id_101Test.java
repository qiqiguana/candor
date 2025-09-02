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
    void testEmptyString() {
        List<Object> result = WordsString.wordsString("");
        assertTrue(result.isEmpty());
    }
    
    @Test
        public void testNothing(){
            WordsString s = new WordsString();
            }
    @Test
    public void testWordsString_EmptyString() {
        List<Object> result = WordsString.wordsString("");
        assertEquals(new ArrayList<>(), result);
    }
    @Test
    public void testWordsString_CommaSeparated() {
        List<Object> result = WordsString.wordsString("One, two, three, four");
        assertEquals(Arrays.asList("One", "two", "three", "four"), result);
    }
                                    
}