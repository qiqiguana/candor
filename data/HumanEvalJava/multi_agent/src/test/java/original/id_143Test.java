package original;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
/**
* Test class of WordsInSentence.
*/
class WordsInSentenceTest {
    @Test
    void testWordsInSentence_withEmptyString_shouldReturnEmptyString() {
        // Arrange
        String sentence = "";
        String expectedResult = "";

        // Act
        String result = WordsInSentence.wordsInSentence(sentence);

        // Assert
        assertEquals(expectedResult, result);
    }
}