package original;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
/**
* Test class of IsHappy.
*/
class IsHappyTest {
    @Test
    void testIsHappy() {
        // given
        String input = "iopaxpoi";
        Boolean expected = true;
        // when
        Boolean result = IsHappy.isHappy(input);
        // then
        assertEquals(expected, result);
    }
}