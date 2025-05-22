package original;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
/**
* Test class of ChooseNum.
*/
class ChooseNumTest {
    @Test
    void testChooseNum_BiggestEvenInRange_ReturnsBiggestEven() {
        int x = 12;
        int y = 15;
        int expectedResult = 14;
        int actualResult = ChooseNum.chooseNum(x, y);
        assertEquals(expectedResult, actualResult);
    }
}
