package original;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
/**
* Test class of Encode.
*/
class EncodeTest {
    @Test
    void testEncodeSwapsCaseAndReplacesVowels() {
        String message = "Hello World";
        String expected = "hGLLQ wQRLD";
        assertEquals(expected, Encode.encode(message));
    }
}
