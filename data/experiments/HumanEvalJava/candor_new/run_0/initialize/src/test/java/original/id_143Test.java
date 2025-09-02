package original;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
/**
* Test class of WordsInSentence.
*/
class WordsInSentenceTest {
    @Test
    void wordsInSentence_withMultiplePrimeLengthWords_shouldReturnThoseWords() {
        String input = "lets go for swimming";
        String expectedOutput = "go for";
        String actualOutput = WordsInSentence.wordsInSentence(input);
        assertEquals(expectedOutput, actualOutput);
    }
}