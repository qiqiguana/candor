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
    
    @Test
        public void testNothing(){
            ChooseNum s = new ChooseNum();
            }
    @Test
    public void testChooseNum_NormalRange() {
        int x = 12;
        int y = 15;
        int expected = 14;
        assertEquals(expected, ChooseNum.chooseNum(x, y));
    }
    @Test
    public void testChooseNum_ReversedRange() {
        int x = 13;
        int y = 12;
        int expected = -1;
        assertEquals(expected, ChooseNum.chooseNum(x, y));
    }
    @Test
    public void testChooseNum_LargeNumbers() {
        int x = 33;
        int y = 12354;
        int expected = 12354;
        assertEquals(expected, ChooseNum.chooseNum(x, y));
    }
    @Test
    public void testChooseNum_SameNumbers() {
        int x = 546;
        int y = 546;
        int expected = 546;
        assertEquals(expected, ChooseNum.chooseNum(x, y));
    }
    @Test
    public void testChooseNum_SmallRange() {
        int x = 6;
        int y = 8;
        int expected = 8;
        assertEquals(expected, ChooseNum.chooseNum(x, y));
    }
    @Test
    public void testChooseNum_NegativeInput_ReturnsMinusOne() {
        int x = -10;
        int y = -5;
        int expected = -1;
        assertEquals(expected, ChooseNum.chooseNum(x, y));
    }
                                    
}