package original;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
/**
* Test class of Encrypt.
*/
class EncryptTest {

    @Test
    void testEncryptShiftsAlphabetByFourPlaces() {
        String input = "abcd";
        String expectedOutput = "efgh";
        assertEquals(expectedOutput, Encrypt.encrypt(input));
    }
}