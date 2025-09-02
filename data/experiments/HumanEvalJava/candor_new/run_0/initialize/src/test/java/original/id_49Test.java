package original;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
/**
* Test class of Modp.
*/
class ModpTest {

    @Test
    void testModP_WhenInputIs0_ResultShouldBe1() {
        int result = Modp.modp(0, 101);
        assertEquals(1, result);
    }
}