package original;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
/**
* Test class of SpecialFactorial.
*/
class SpecialFactorialTest {
    @Test
    void testSpecialFactorial() {
        assertEquals(0, SpecialFactorial.specialFactorial(4));
    }
}