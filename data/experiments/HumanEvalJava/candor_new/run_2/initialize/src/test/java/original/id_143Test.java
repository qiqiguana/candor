package original;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
/**
* Test class of WordsInSentence.
*/
class WordsInSentenceTest {
    @Test
    void testWordsInSentence_withSentenceContainingPrimeLengthWords_returnsWordsWithPrimeLengths() {
        String sentence = "lets go for swimming";
        String expected = "go for";
        String actual = WordsInSentence.wordsInSentence(sentence);
        assertEquals(expected, actual);
    }
}