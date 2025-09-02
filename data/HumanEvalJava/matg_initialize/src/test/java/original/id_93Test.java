package original;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
/**
* Test class of Encode.
*/
class EncodeTest {
    @Test
    void testEncode() {
        String message = "test";
        String expected = "TGST";
        String actual = Encode.encode(message);
        assertEquals(expected, actual);
    }
}