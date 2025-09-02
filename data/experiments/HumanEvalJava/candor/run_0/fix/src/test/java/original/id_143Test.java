package original;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
/**
* Test class of WordsInSentence.
*/
class WordsInSentenceTest {
    @Test
    void wordsInSentence_SingleWord_NoPrimeLength_ReturnsEmptyString() {
        String sentence = "here";
        String expected = "";
        String actual = WordsInSentence.wordsInSentence(sentence);
        assertEquals(expected, actual);
    }
    
    @Test
        void testNothing(){
            WordsInSentence s = new WordsInSentence();
            }
    @Test
    public void emptySentenceTest() {
        String input = "";
        String expectedOutput = "";
        assertEquals(expectedOutput, WordsInSentence.wordsInSentence(input));
    }
    @Test
    public void singleWordPrimeLengthTest() {
        String input = "ab";
        String expectedOutput = "ab";
        assertEquals(expectedOutput, WordsInSentence.wordsInSentence(input));
    }
    @Test
    public void singleWordNonPrimeLengthTest() {
        String input = "abcd";
        String expectedOutput = "";
        assertEquals(expectedOutput, WordsInSentence.wordsInSentence(input));
    }
    @Test
    public void multipleWordsPrimeLengthsTest() {
        String input = "ab cd ef";
        String expectedOutput = "ab cd ef";
        assertEquals(expectedOutput, WordsInSentence.wordsInSentence(input));
    }
    @Test
    public void multipleWordsNonPrimeLengthsTest() {
        String input = "abcd efgh ijkl";
        String expectedOutput = "";
        assertEquals(expectedOutput, WordsInSentence.wordsInSentence(input));
    }
    @Test
    public void mixedWordsPrimeAndNonPrimeLengthsTest() {
        String input = "ab cd efgh ij";
        String expectedOutput = "ab cd ij";
        assertEquals(expectedOutput, WordsInSentence.wordsInSentence(input));
    }
    @Test
    public void largeInputTest() {
        String input = "ab cd ef gh ij kl mn op qr st uv wx yz";
        String expectedOutput = "ab cd ef gh ij kl mn op qr st uv wx yz";
        assertEquals(expectedOutput, WordsInSentence.wordsInSentence(input));
    }
                                    
}