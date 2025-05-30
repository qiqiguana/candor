package oracle1;

import java.util.Arrays;
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
        List<Object> numbers = new ArrayList<>();
        List<Integer> result = SumProduct.sumProduct(numbers);
        assertEquals(0, (int)result.get(0)); // sum should be 0 for an empty list
    }
    
    @Test
     void testNothing(){
         SumProduct s = new SumProduct();
         }
    @Test
    public void testSumProduct_EmptyList() {
        List<Object> input = new ArrayList<>();
        List<Integer> result = SumProduct.sumProduct(input);
        assertEquals(0, (int)result.get(0));
        assertEquals(1, (int)result.get(1));
    }
    @Test
    public void testSumProduct_SingleElement() {
        List<Object> input = Arrays.asList(10);
        List<Integer> result = SumProduct.sumProduct(input);
        assertEquals(10, (int)result.get(0));
    }
    @Test
    public void testSumProduct_MultipleElements_1() {
        List<Object> input = Arrays.asList(1, 2, 3, 4);
        List<Integer> result = SumProduct.sumProduct(input);
        assertEquals(10, (int)result.get(0));
    }
    @Test
    public void testSumProduct_DuplicateElements() {
        List<Object> input = Arrays.asList(1, 1, 1);
        List<Integer> result = SumProduct.sumProduct(input);
        assertEquals(3, (int)result.get(0));
    }
    @Test
    public void testSumProduct_NonIntegerElement_1() {
        List<Object> input = Arrays.asList(1, "a", 3);
        assertThrows(IllegalArgumentException.class, () -> SumProduct.sumProduct(input));
    }
                                  
}