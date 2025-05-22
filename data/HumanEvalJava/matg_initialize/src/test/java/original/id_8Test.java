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
    void testSumProductNonEmptyList() {
        List<Object> numbers = new ArrayList<>();
        numbers.add(1);
        numbers.add(2);
        numbers.add(3);
        numbers.add(4);
        List<Integer> result = SumProduct.sumProduct(numbers);
        assertEquals(10, (int)result.get(0)); // assert sum is correct
    }
}