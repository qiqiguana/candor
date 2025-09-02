package original;

import java.util.List;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
* Test class of SumSquares.
*/
class SumSquaresTest {
    @Test
    void testSumSquaresCeilingRounding() {
        List<Number> numbers = List.of(1.4, 4.2, 0);
        int expectedSum = 29;
        assertEquals(expectedSum, SumSquares.sumSquares(numbers));
    }
    
    @Test
        public void testNothing(){
            SumSquares s = new SumSquares();
            }
                                    
}