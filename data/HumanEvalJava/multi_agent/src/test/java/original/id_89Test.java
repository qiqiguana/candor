package original;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
/**
* Test class of Encrypt.
*/
class EncryptTest {
    @Test
    void testEncrypt() {
        String actual = Encrypt.encrypt("hi");
        String expected = "lm";
        assertEquals(expected, actual);
    }
}
