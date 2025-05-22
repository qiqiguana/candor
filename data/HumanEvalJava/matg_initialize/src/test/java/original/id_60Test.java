package original;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
/**
* Test class of SumToN.
*/
class SumToNTest {
    @Test
    void testSumToNSimpleCase() {
        assertEquals(15, SumToN.sumToN(5));
    }
}