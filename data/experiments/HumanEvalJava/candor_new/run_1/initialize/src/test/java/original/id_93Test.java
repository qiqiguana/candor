package original;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
/**
* Test class of Encode.
*/
class EncodeTest {
    @Test
    void testEncode_SingleWord_LowerCaseToUpperCase() {
        String input = "test";
        String expectedOutput = "TGST";
        String actualOutput = Encode.encode(input);
        assertEquals(expectedOutput, actualOutput);
    }
}