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
    void testSumProductWithEmptyList() {
        List<Object> numbers = new ArrayList<>();
        List<Integer> expected = new ArrayList<>();
        expected.add(0);
        expected.add(1);
        assertEquals(expected, SumProduct.sumProduct(numbers));
    }
    
    @Test
        public void testNothing(){
            SumProduct s = new SumProduct();
            }
    @Test
    public void testSumProductSingleElement() {
        List<Object> numbers = new ArrayList<>();
        numbers.add(5);
        List<Integer> result = SumProduct.sumProduct(numbers);
        assertEquals(2, result.size());
        assertEquals(5, (int)result.get(0));
        assertEquals(5, (int)result.get(1));
    }
    @Test
    public void testSumProductNonIntegerElement() {
        List<Object> numbers = new ArrayList<>();
        numbers.add(1);
        numbers.add("a");
        numbers.add(3);
        assertThrows(IllegalArgumentException.class, () -> SumProduct.sumProduct(numbers));
    }
                                    
}