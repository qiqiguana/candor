package original;

import java.util.List;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
* Test class of AddElements.
*/
class AddElementsTest {
    @Test
    void testAddElements() {
        List<Integer> list = List.of(111, 21, 3, 4000, 5, 6, 7, 8, 9);
        int result = AddElements.addElements(list, 4);
        assertEquals(24, result);
    }
}