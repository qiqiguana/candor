package original;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
/**
* Test class of RoundedAvg.
*/
class RoundedAvgTest {
    @Test
    void testRoundedAvg_nLessThanM() {
        assertEquals("0b11", RoundedAvg.roundedAvg(1, 5));
    }
}