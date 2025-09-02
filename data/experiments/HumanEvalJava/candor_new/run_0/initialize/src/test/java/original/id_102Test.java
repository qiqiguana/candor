package original;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
/**
* Test class of ChooseNum.
*/
class ChooseNumTest {
    @Test
    void testChooseNum_SwapInput_ReturnsNegativeOne() {
        int result = ChooseNum.chooseNum(13, 12);
        assertEquals(-1, result);
    }
}