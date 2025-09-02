package original;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
/**
* Test class of CheckIfLastCharIsALetter.
*/
class CheckIfLastCharIsALetterTest {
    @Test
    void checkIfLastCharIsALetter_WhenStringIsEmpty_ReturnsFalse() {
        // Arrange and Act
        Boolean result = CheckIfLastCharIsALetter.checkIfLastCharIsALetter("");
        // Assert
        assertFalse(result);
    }
    
    @Test
        public void testNothing(){
            CheckIfLastCharIsALetter s = new CheckIfLastCharIsALetter();
            }
    @Test
    public void testEmptyString() {
        assertFalse(CheckIfLastCharIsALetter.checkIfLastCharIsALetter(""));
    }
    @Test
    public void testSingleCharacterLetter() {
        assertTrue(CheckIfLastCharIsALetter.checkIfLastCharIsALetter("A"));
    }
    @Test
    public void testSingleCharacterNonLetter() {
        assertFalse(CheckIfLastCharIsALetter.checkIfLastCharIsALetter("1"));
    }
    @Test
    public void testMultipleCharactersEndingWithLetter() {
        assertTrue(CheckIfLastCharIsALetter.checkIfLastCharIsALetter("apple pi e"));
    }
    @Test
    public void testMultipleCharactersEndingWithNonLetter() {
        assertFalse(CheckIfLastCharIsALetter.checkIfLastCharIsALetter("apple pie 1"));
    }
    @Test
    public void testMultipleCharactersEndingWithLetterAndTrailingSpace() {
        assertFalse(CheckIfLastCharIsALetter.checkIfLastCharIsALetter("apple pi e "));
    }
    @Test
    public void testEdgeCase1() {
        assertFalse(CheckIfLastCharIsALetter.checkIfLastCharIsALetter(" "));
    }
    @Test
    public void test_checkIfLastCharIsALetter_SingleLetter() {
        String input = "a";
        Boolean expectedOutput = true;
        assertEquals(expectedOutput, CheckIfLastCharIsALetter.checkIfLastCharIsALetter(input));
    }
    @Test
    public void test_checkIfLastCharIsALetter_MultipleLettersAndSpaces() {
        String input = "apple pie";
        Boolean expectedOutput = false;
        assertEquals(expectedOutput, CheckIfLastCharIsALetter.checkIfLastCharIsALetter(input));
    }
    @Test
    public void test_checkIfLastCharIsALetter_SingleWordNoSpace() {
        String input = "apple";
        Boolean expectedOutput = false;
        assertEquals(expectedOutput, CheckIfLastCharIsALetter.checkIfLastCharIsALetter(input));
    }
    @Test
    public void test_checkIfLastCharIsALetter_MultipleWordsAndLastCharAsLetter() {
        String input = "apple pi e";
        Boolean expectedOutput = true;
        assertEquals(expectedOutput, CheckIfLastCharIsALetter.checkIfLastCharIsALetter(input));
    }
    @Test
    public void test_checkIfLastCharIsALetter_EmptyString() {
        String input = "";
        Boolean expectedOutput = false;
        assertEquals(expectedOutput, CheckIfLastCharIsALetter.checkIfLastCharIsALetter(input));
    }
                                    
}