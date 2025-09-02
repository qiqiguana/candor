package original;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
/**
* Test class of Encrypt.
*/
class EncryptTest {
    @Test
    void testEncrypt() {
        String input = "hi";
        String expectedOutput = "lm";
        String actualOutput = Encrypt.encrypt(input);
        assertEquals(expectedOutput, actualOutput);
    }
}