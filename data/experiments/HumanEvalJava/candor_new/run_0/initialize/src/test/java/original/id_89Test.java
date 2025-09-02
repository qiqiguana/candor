package original;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
/**
* Test class of Encrypt.
*/
class EncryptTest {
    @Test
    void testEncryptFunctionality() {
        String input = "hello";
        String expectedOutput = "lipps";
        assertEquals(expectedOutput, Encrypt.encrypt(input));
    }
}
