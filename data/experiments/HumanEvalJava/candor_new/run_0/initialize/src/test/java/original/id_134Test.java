package original;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
/**
* Test class of CheckIfLastCharIsALetter.
*/
class CheckIfLastCharIsALetterTest {
    @Test
    void test_checkIfLastCharIsALetter_LastCharacterIsLetterAndNotPartOfWord_ReturnsTrue() {
        String txt = "apple pi e";
        Boolean expected = true;
        assertEquals(expected, CheckIfLastCharIsALetter.checkIfLastCharIsALetter(txt));
    }
}