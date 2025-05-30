package original;

import java.util.ArrayList;

import java.util.List;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
/**
* Test class of SumProduct.
*/
class SumProductTest {
    @Test
    void testSumProduct_EmptyList_ReturnsZeroSumAndOneProduct() {
        // Arrange
        List<Object> numbers = new ArrayList<>();
        Integer[] expected = {0, 1};

        // Act
        List<Integer> result = SumProduct.sumProduct(numbers);

        // Assert
        assertArrayEquals(expected, result.toArray());
    }
    @Test
    void testSumProduct_withEmptyList() {
        List<Object> input = new ArrayList<>();
        Integer expectedSum = 0;
        Integer expectedProduct = 1;
        List<Integer> result = SumProduct.sumProduct(input);
        assertEquals(expectedSum, result.get(0));
        assertEquals(expectedProduct, result.get(1));
    }
    @Test
    public void testSumProduct_1() {
        List<Object> numbers = new ArrayList<>();
        List<Integer> result = SumProduct.sumProduct(numbers);
        assertEquals(2, result.size());
        assertEquals(0, (int)result.get(0));
        assertEquals(1, (int)result.get(1));
    }
    @Test
    public void testSumProductWithEmptyList1() {
        List<Object> input = new ArrayList<>();
        List<Integer> result = SumProduct.sumProduct(input);
        assertEquals(0, (int)result.get(0));
        assertEquals(1, (int)result.get(1));
    }
    @Test
    public void testSumProductWithNullList() {
        List<Object> input = null;
        assertThrows(NullPointerException.class, () -> SumProduct.sumProduct(input));
    }
}