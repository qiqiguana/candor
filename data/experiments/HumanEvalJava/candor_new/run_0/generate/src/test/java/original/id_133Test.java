package original;

import java.util.List;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

/**
* Test class of SumSquares.
*/
class SumSquaresTest {
    @Test
    void testSumSquares() {
        List<Number> numbers = List.of(1, 2, 3);
        int expected = 14;
        assertEquals(expected, SumSquares.sumSquares(numbers));
    }
    
    @Test
        public void testNothing(){
            SumSquares s = new SumSquares();
            }
                                    
}