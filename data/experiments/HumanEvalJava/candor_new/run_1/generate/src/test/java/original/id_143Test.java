package original;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
/**
* Test class of WordsInSentence.
*/
class WordsInSentenceTest {
    @Test
    void testWordsWithPrimeLengthsAreReturned() {
        String sentence = "This is a test";
        String result = WordsInSentence.wordsInSentence(sentence);
        assertEquals("is", result);
    }
    
    @Test
        public void testNothing(){
            WordsInSentence s = new WordsInSentence();
            }
    @Test
    public void testWordsInSentenceWithPrimeLengthWordAtTheEnd() {
        String input = "there is no place available here";
        String expectedOutput = "there is no place";
        assertEquals(expectedOutput, WordsInSentence.wordsInSentence(input));
    }
                                    
}