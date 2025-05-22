package original;

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
}
