package original;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
/**
* Test class of Encode.
*/
class EncodeTest {
    @Test
    void testEncode_SwapsCaseAndReplacesVowels() {
        String message = "This is a message";
        String expectedResult = "tHKS KS C MGSSCGG";
        assertEquals(expectedResult, Encode.encode(message));
    }
}
