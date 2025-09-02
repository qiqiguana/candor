package original;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
/**
* Test class of RoundedAvg.
*/
class RoundedAvgTest {
    @Test
    void testRoundedAvg_NLessThanOrEqualToM_ReturnsBinaryString() {
        String result = (String) RoundedAvg.roundedAvg(1, 5);
        assertEquals("0b11", result);
    }
}