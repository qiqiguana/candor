package original;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
/**
* Test class of ChooseNum.
*/
class ChooseNumTest {
    @Test
    void testChooseNum_EvenNumberInRange_ReturnsZero() {
        int result = ChooseNum.chooseNum(10, 20);
        assertEquals(0, result);
    }
}