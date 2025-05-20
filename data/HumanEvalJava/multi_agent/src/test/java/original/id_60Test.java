package original;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
/**
* Test class of SumToN.
*/
class SumToNTest {
    @Test
    void testSumToN_SimpleCase() {
        assertEquals(55, SumToN.sumToN(10));
    }
}