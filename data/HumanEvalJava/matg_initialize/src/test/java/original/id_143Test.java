package original;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
/**
* Test class of WordsInSentence.
*/
class WordsInSentenceTest {

    @Test
    void testWordsInSentence_withSinglePrimeWordLength_shouldReturnThatWord() {
        // Arrange and Act
        String result = WordsInSentence.wordsInSentence("is");

        // Assert
        assertEquals("is", result);
    }
}