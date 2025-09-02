package original;

import java.util.ArrayList;

import java.util.List;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
/**
* Test class of SumSquares1.
*/
class SumSquares1Test {
    @Test
    void testSumSquares_CorrectInput() {
        List<Object> input = new ArrayList<>();
        input.add(1);
        input.add(2);
        input.add(3);
        int expected = 6;
        int actual = SumSquares1.sumSquares(input);
        assertEquals(expected, actual);
    }
}