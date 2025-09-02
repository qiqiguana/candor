package original;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
/**
* Test class of StartsOneEnds.
*/
class StartsOneEndsTest {
    @Test
    void testStartsOneEndsWithLength1() {
        assertEquals(1, StartsOneEnds.startsOneEnds(1));
    }
}