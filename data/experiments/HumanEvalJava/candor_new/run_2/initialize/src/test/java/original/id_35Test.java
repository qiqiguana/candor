package original;

import java.util.List;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
* Test class of MaxElement.
*/
class MaxElementTest {
    @Test
    void testMaxElement() {
        List<Integer> list = List.of(1, 2, 3);
        int expected = 3;
        assertEquals(expected, MaxElement.maxElement(list));
    }
}