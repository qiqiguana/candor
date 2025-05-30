package original;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
/**
* Test class of StartsOneEnds.
*/
class StartsOneEndsTest {
    @Test
    void testStartsOneEndsWithN2() {
        int n = 1;
        int expected = 0;
        assertEquals(expected, StartsOneEnds.startsOneEnds(n));
    }
}
