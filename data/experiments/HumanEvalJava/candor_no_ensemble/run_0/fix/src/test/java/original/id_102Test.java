package original;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
/**
* Test class of ChooseNum.
*/
class ChooseNumTest {
    @Test
    void testChooseNum_LargestEvenNumberInRange_ReturnsCorrectResult() {
        int x = 12;
        int y = 15;
        int expectedResult = 14;
        int actualResult = ChooseNum.chooseNum(x, y);
        assertEquals(expectedResult, actualResult);
    }
    
    @Test
     void testNothing(){
         ChooseNum s = new ChooseNum();
         }
    @Test
    public void testChooseNumWithXLessThanYAndBothNumbersAreEven() {
        int x = 12;
        int y = 14;
        int expectedResult = 14;
        assertEquals(expectedResult, ChooseNum.chooseNum(x, y));
    }
    @Test
    public void testChooseNumWithXGreaterThanY() {
        int x = 13;
        int y = 12;
        int expectedResult = -1;
        assertEquals(expectedResult, ChooseNum.chooseNum(x, y));
    }
    @Test
    public void testChooseNumWithXEqualToYAndBothNumbersAreEven() {
        int x = 546;
        int y = 546;
        int expectedResult = 546;
        assertEquals(expectedResult, ChooseNum.chooseNum(x, y));
    }
    @Test
    public void testChooseNumWithXLessThanYAndBothNumbersAreOddFixed() {
        int x = 13;
        int y = 15;
        int expectedResult = 14;
        assertEquals(expectedResult, ChooseNum.chooseNum(x, y));
    }
                                  
}