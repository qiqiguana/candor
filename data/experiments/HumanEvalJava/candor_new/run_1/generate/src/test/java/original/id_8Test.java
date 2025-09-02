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
    void testSumProduct_ReturnsCorrectResult_WhenListContainsMultipleNumbers() {
        List<Object> numbers = new ArrayList<>();
        numbers.add(1);
        numbers.add(2);
        numbers.add(3);
        List<Integer> result = SumProduct.sumProduct(numbers);
        assertEquals(6, result.get(0)); // assert sum is correct
    }
    
    @Test
        public void testNothing(){
            SumProduct s = new SumProduct();
            }
    @Test
    public void testSumProductWithInvalidNumberType() {
        List<Object> numbers = new ArrayList<>();
        numbers.add(new Object());
        numbers.add(1);
        assertThrows(IllegalArgumentException.class, () -> SumProduct.sumProduct(numbers));
    }
                                    
}