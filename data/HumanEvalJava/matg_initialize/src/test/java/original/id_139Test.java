package original;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
/**
* Test class of SpecialFactorial.
*/
class SpecialFactorialTest {
    @Test
    void testSpecialFactorial_SimpleCase() {
        long result = SpecialFactorial.specialFactorial(4);
        assertEquals(288, result);
    }
}