package original;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
/**
* Test class of GetMaxTriples.
*/
class GetMaxTriplesTest {

    @Test
    void testGetMaxTriples() {
        int result = GetMaxTriples.getMaxTriples(5);
        assertEquals(1, result);
    }
}