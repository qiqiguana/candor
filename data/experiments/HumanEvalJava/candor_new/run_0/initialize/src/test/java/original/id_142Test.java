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
    void testSumSquares_WhenEmptyList_ReturnZero() {
        List<Object> list = new ArrayList<>();
        int result = SumSquares1.sumSquares(list);
        assertEquals(0, result);
    }
}