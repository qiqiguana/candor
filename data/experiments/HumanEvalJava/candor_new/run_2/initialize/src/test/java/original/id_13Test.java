package original;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
/**
* Test class of GreatestCommonDivisor.
*/
class GreatestCommonDivisorTest {
    @Test
    void testGreatestCommonDivisor() {
        assertEquals(1, GreatestCommonDivisor.greatestCommonDivisor(3, 5));
    }
}