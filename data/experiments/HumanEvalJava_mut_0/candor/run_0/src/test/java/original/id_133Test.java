package original;

import java.util.List;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
* Test class of SumSquares.
*/
class SumSquaresTest {
    @Test
    void testSumSquares_EmptyList_ReturnsZero() {
        List<Number> numbers = List.of();
        int result = SumSquares.sumSquares(numbers);
        assertEquals(0, result);
    }
}