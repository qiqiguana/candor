package original;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
/**
* Test class of ChooseNum.
*/
class ChooseNumTest {

    @Test
    void testChooseNum_SameInput_OutputIsNumber() {
        // Arrange & Act
        int result = ChooseNum.chooseNum(546, 546);
        // Assert
        assertEquals(546, result);
    }
}