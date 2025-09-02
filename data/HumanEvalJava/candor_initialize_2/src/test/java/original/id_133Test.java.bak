package original;

import java.util.List;
import java.util.ArrayList;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
/**
* Test class of SumSquares.
*/
class SumSquaresTest {
    @Test
    void testSumSquares() {
        List<Number> lst = new ArrayList<>();
        lst.add(1);
        lst.add(2);
        lst.add(3);
        assertEquals(14, SumSquares.sumSquares(lst));
    }
    @Test
    public void testSumSquaresWithEmptyList() {
        List<Number> input = new ArrayList<>();
        int expected = 0;
        assertEquals(expected, SumSquares.sumSquares(input));
    }
    @Test
    public void testSumSquaresPositiveIntegers() {
        List<Number> input = List.of(1, 2, 3);
        int expectedResult = 14;
        assertEquals(expectedResult, SumSquares.sumSquares(input));
    }
    @Test
    public void testSumSquaresNegativeIntegers() {
        List<Number> input = List.of(-1, -2, -3);
        int expectedResult = 14;
        assertEquals(expectedResult, SumSquares.sumSquares(input));
    }
    @Test
    public void testSumSquaresMixedIntegers() {
        List<Number> input = List.of(1, -2, 3);
        int expectedResult = 14;
        assertEquals(expectedResult, SumSquares.sumSquares(input));
    }
    @Test
    public void testSumSquaresDecimalNumbers() {
        List<Number> input = List.of(1.4, 2.5, 3.6);
        int expectedResult = 29;
        assertEquals(expectedResult, SumSquares.sumSquares(input));
    }
    @Test
    public void testSumSquaresEmptyList() {
        List<Number> input = List.of();
        int expectedResult = 0;
        assertEquals(expectedResult, SumSquares.sumSquares(input));
    }
    @Test
    public void NegativeTest_SumSquares_SadPath_EmptyList() {
        List<Number> lst = new ArrayList<>();
        assertEquals(0, SumSquares.sumSquares(lst));
    }
}