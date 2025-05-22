package original;

import java.util.Arrays;
import java.util.List;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
* Test class of SumSquares.
*/
class SumSquaresTest {

    @Test
    void testSumSquares() {
        List<Number> list = List.of(1, 2, 3);
        int expected = 14;
        int actual = SumSquares.sumSquares(list);
        assertEquals(expected, actual);
    }
    
    @Test
        public void testNothing(){
            SumSquares s = new SumSquares();
            }
    @Test
    public void NegativeTest_SadPath_InvalidInput_NullList() {
        List<Number> input = null;
        assertThrows(NullPointerException.class, () -> SumSquares.sumSquares(input));
    }
    @Test
    public void PositiveTest_HappyPath_ValidInput() {
        List<Number> input = Arrays.asList(1, 2, 3);
        int expected = 14;
        assertEquals(expected, SumSquares.sumSquares(input));
    }
                                    
}