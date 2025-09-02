package original;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
/**
* Test class of Add.
*/
class AddTest {
    @Test
    void testAdd() {
        assertEquals(1795, new Add().add(842, 953));
    }
}
