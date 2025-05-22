package original;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
* Test class of Intersperse.
*/
class IntersperseTest {
    @Test
    void testInterspersedListWithDelimiter() {
        List<Object> numbers = new ArrayList<>(List.of(1, 2, 3));
        int delimiter = 4;
        List<Object> expected = new ArrayList<>(List.of(1, 4, 2, 4, 3));
        assertEquals(expected, Intersperse.intersperse(numbers, delimiter));
    }
}