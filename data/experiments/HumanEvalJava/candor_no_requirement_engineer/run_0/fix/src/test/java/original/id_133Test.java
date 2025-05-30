package original;

import static org.junit.jupiter.api.Assertions.assertEquals;
import java.util.Arrays;
import java.util.List;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
* Test class of SumSquares.
*/
class SumSquaresTest {
    @Test
    void testSumSquaresCeilingFunctionality() {
        List<Number> numbers = List.of(1.4, 4.2, 0);
        int expected = 29;
        assertEquals(expected, SumSquares.sumSquares(numbers));
    }
    
    @Test
     void testNothing(){
         SumSquares s = new SumSquares();
         }
    @Test
    public void test_sumSquares_HappyPath_IntegerInput() {
        List<Number> numbers = Arrays.asList(1, 2, 3);
        int result = SumSquares.sumSquares(numbers);
        assertEquals(14, result);
    }
                                  
}