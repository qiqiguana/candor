package original;

import java.util.List;
import java.util.ArrayList;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
/**
* Test class of Longest.
*/
class LongestTest {
    @Test
    void testLongest_EmptyList_ReturnsNull() {
        List<Object> strings = new ArrayList<>();
        String result = Longest.longest(strings);
        assertNull(result);
    }
}
