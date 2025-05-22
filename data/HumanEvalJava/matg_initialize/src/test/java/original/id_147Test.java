package original;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
/**
* Test class of GetMaxTriples.
*/
class GetMaxTriplesTest {
    @Test
    void testGetMaxTriples_smallInput() {
        int n = 5;
        int expectedResult = 1;
        int actualResult = GetMaxTriples.getMaxTriples(n);
        assertEquals(expectedResult, actualResult);
    }
}