package original;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
/**
* Test class of GetMaxTriples.
*/
class GetMaxTriplesTest {

    @Test
    void testGetMaxTriples_smallInput() {
        assertEquals(1, GetMaxTriples.getMaxTriples(5));
    }
}