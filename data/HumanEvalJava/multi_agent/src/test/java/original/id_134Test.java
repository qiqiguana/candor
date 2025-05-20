package original;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
/**
* Test class of CheckIfLastCharIsALetter.
*/
class CheckIfLastCharIsALetterTest {
    @Test
    void test_checkIfLastCharIsALetter_withSingleCharacterReturnsTrue() {
        assertTrue(CheckIfLastCharIsALetter.checkIfLastCharIsALetter("A"));
    }
}