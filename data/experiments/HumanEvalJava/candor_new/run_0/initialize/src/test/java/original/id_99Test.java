package original;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
/**
* Test class of ClosestInteger.
*/
class ClosestIntegerTest {
    @Test
    void test_closestInteger_givenValue_14Point5_return15() {
        String value = "14.5";
        int expected = 15;
        assertEquals(expected, ClosestInteger.closestInteger(value));
    }
}