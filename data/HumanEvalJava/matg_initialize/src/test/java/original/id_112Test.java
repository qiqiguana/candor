package original;

import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
/**
* Test class of ReverseDelete.
*/
class ReverseDeleteTest {
    @Test
    void testReverseDelete_SingleCharacterString_ReturnsEmptyStringAndTrue() {
        List<Object> result = ReverseDelete.reverseDelete("a", "a");
        assertEquals(2, result.size());
        assertEquals("", result.get(0));
        assertTrue((Boolean) result.get(1));
    }
}