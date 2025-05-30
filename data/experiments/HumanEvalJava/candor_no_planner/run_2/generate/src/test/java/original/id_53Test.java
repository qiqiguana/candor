package original;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
/**
* Test class of Add.
*/
class AddTest {
    @Test
    void testAdd() {
        int x = 2;
        int y = 3;
        int expected = 5;
        assertEquals(expected, Add.add(x, y));
    }
}