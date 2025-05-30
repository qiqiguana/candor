package oracle1;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
/**
* Test class of CheckIfLastCharIsALetter.
*/
class CheckIfLastCharIsALetterTest {
    @Test
    void testCheckIfLastCharIsALetter_LastCharIsLetterAndNotPartOfWord_ReturnsTrue() {
        String txt = "apple pi e";
        assertTrue(CheckIfLastCharIsALetter.checkIfLastCharIsALetter(txt));
    }
    
    @Test
        void testNothing(){
            CheckIfLastCharIsALetter s = new CheckIfLastCharIsALetter();
            }
    @Test
    public void testEmptyString() {
    	assertFalse(CheckIfLastCharIsALetter.checkIfLastCharIsALetter(""));
    }
    @Test
    public void testSingleCharacterLetter() {
    	assertTrue(CheckIfLastCharIsALetter.checkIfLastCharIsALetter("a"));
    }
    @Test
    public void testSingleCharacterNonLetter() {
    	assertFalse(CheckIfLastCharIsALetter.checkIfLastCharIsALetter("1"));
    }
    @Test
    public void testMultipleCharactersLastLetter() {
    	assertTrue(CheckIfLastCharIsALetter.checkIfLastCharIsALetter("apple pie e"));
    }
    @Test
    public void testMultipleCharactersLastNonLetter() {
    	assertFalse(CheckIfLastCharIsALetter.checkIfLastCharIsALetter("apple pie 1"));
    }
    @Test
    public void testWordWithTrailingSpace() {
    	assertFalse(CheckIfLastCharIsALetter.checkIfLastCharIsALetter("apple pie "));
    }
    @Test
    public void testEmptyString1() {
    	assertFalse(CheckIfLastCharIsALetter.checkIfLastCharIsALetter(""));
    }
    @Test
    public void test_empty_string() {
       assertFalse(CheckIfLastCharIsALetter.checkIfLastCharIsALetter(""));
    }
    @Test
    public void test_single_letter() {
       assertTrue(CheckIfLastCharIsALetter.checkIfLastCharIsALetter("A"));
    }
    @Test
    public void test_multiple_letters() {
       assertFalse(CheckIfLastCharIsALetter.checkIfLastCharIsALetter("eeeee"));
    }
    @Test
    public void test_single_word() {
       assertFalse(CheckIfLastCharIsALetter.checkIfLastCharIsALetter("apple"));
    }
    @Test
    public void test_multiple_words() {
       assertFalse(CheckIfLastCharIsALetter.checkIfLastCharIsALetter("apple pie"));
    }
    @Test
    public void test_single_letter_and_space_1() {
       assertTrue(CheckIfLastCharIsALetter.checkIfLastCharIsALetter(" A"));
    }
    @Test
    public void test_single_letter_and_word() {
       assertFalse(CheckIfLastCharIsALetter.checkIfLastCharIsALetter("Aapple"));
    }
    @Test
    public void test_single_letter_and_space() {
       assertFalse(CheckIfLastCharIsALetter.checkIfLastCharIsALetter("A "));
    }
    @Test
    public void test_word_and_single_letter_corrected_1() {
       assertFalse(CheckIfLastCharIsALetter.checkIfLastCharIsALetter("apple A"));
    }
                                    
}