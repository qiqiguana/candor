package original;

import java.util.List;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
* Test class of Longest.
*/
class LongestTest {
    @Test
    void testLongest_WithMultipleStrings_ReturnsFirstLongestString() {
        List<Object> strings = List.of("a", "bb", "ccc");
        String result = Longest.longest(strings);
        assertEquals("ccc", result);
    }
}