package original;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
/**
* Test class of AnyInt.
*/
class AnyIntTest {
    @Test
    void testAnyInt_True() {
        assertTrue(AnyInt.anyInt(5, 2, 7));
    }
}