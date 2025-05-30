package original;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
/**
* Test class of WordsInSentence.
*/
class WordsInSentenceTest {
    @Test
    void wordsInSentence_withPrimeLengthWords_shouldReturnTheseWords() {
        String sentence = "lets go for swimming";
        String expected = "go for";
        String result = WordsInSentence.wordsInSentence(sentence);
        assertEquals(expected, result);
    }
    
    @Test
        public void testNothing(){
            WordsInSentence s = new WordsInSentence();
            }
    @Test
    public void testWordsInSentence_with_empty_string() {
        String result = WordsInSentence.wordsInSentence("");
        assertEquals("", result);
    }
                                    
}