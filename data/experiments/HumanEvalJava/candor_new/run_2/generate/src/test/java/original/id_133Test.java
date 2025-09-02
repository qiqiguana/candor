package original;

import java.util.List;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import java.util.ArrayList;
/**
* Test class of SumSquares.
*/
class SumSquaresTest {
    @Test
    void testSumSquaresCeilingRounding() {
        List<Number> numbers = new ArrayList<>();
        numbers.add(1.4);
        numbers.add(4.2);
        numbers.add(0);
        int expected = 29;
        assertEquals(expected, SumSquares.sumSquares(numbers));
    }
    
    @Test
        public void testNothing(){
            SumSquares s = new SumSquares();
            }
                                    
}