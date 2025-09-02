package original;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
/**
* Test class of ChooseNum.
*/
class ChooseNumTest {
    @Test
    void testChooseNum_WhenInputRangeContainsEvenNumbers_ReturnsLargestEvenNumber() {
        int result = ChooseNum.chooseNum(12, 15);
        assertEquals(14, result);
    }
    
    @Test
        public void testNothing(){
            ChooseNum s = new ChooseNum();
            }
    @Test
    public void testChooseNum_XAsNegative() {
        int x = -2;
        int y = 10;
        int expectedResult = 10;
        int actualResult = ChooseNum.chooseNum(x, y);
        assertEquals(expectedResult, actualResult);
    }
                                    
}