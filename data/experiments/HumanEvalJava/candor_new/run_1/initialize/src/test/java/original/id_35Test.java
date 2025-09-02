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
        List<Integer> list = List.of(5, 3, -5, 2, -3, 3, 9, 0, 124, 1, -10);
        assertEquals(124, MaxElement.maxElement(list));
    }
}