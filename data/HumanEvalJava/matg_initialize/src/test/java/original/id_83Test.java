package original;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
/**
* Test class of StartsOneEnds.
*/
class StartsOneEndsTest {
    @Test
    void testStartsOneEndsWithSmallInput() {
        assertEquals(18, StartsOneEnds.startsOneEnds(2));
    }
}