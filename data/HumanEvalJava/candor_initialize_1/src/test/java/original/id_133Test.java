package original;

import java.util.List;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
* Test class of SumSquares.
*/
class SumSquaresTest {
    @Test
    void testSumSquares_RoundUpper_ReturnCorrectResult() {
        List<Number> numbers = List.of(1.4, 4.2, 0);
        int result = SumSquares.sumSquares(numbers);
        assertEquals(29, result);
    }
    @Test
    public void testSumSquares_NullInput() {
        List<Number> input = null;
        assertThrows(NullPointerException.class, () -> SumSquares.sumSquares(input));
    }
    @Test
    public void testSumSquaresWithPositiveNumbers() {
    	List<Number> input = List.of(1, 2, 3);
    	int expected = 14;
    	assertEquals(expected, SumSquares.sumSquares(input));
    }
    @Test
    public void testSumSquaresWithNegativeNumbers() {
    	List<Number> input = List.of(-1, -2, -3);
    	int expected = 14;
    	assertEquals(expected, SumSquares.sumSquares(input));
    }
    @Test
    public void testSumSquaresWithMixedNumbers() {
    	List<Number> input = List.of(1, -2, 3, -4);
    	int expected = 30;
    	assertEquals(expected, SumSquares.sumSquares(input));
    }
    @Test
    public void testSumSquaresWithDecimalNumbers21() {
        List<Number> input = List.of(1.5, 2.5, 3.5);
        int expected = (int) Math.pow(Math.ceil(1.5), 2) + (int) Math.pow(Math.ceil(2.5), 2) + (int) Math.pow(Math.ceil(3.5), 2);
        assertEquals(expected, SumSquares.sumSquares(input));
    }
    @Test
    public void testSumSquaresWithEmptyList() {
    	List<Number> input = List.of();
    	int expected = 0;
    	assertEquals(expected, SumSquares.sumSquares(input));
    }
}