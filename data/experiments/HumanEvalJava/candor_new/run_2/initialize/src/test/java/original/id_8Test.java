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
    void testSumProduct_withEmptyList_returnsExpectedResult() {
        List<Object> numbers = new ArrayList<>();
        List<Integer> result = SumProduct.sumProduct(numbers);
        assertEquals(0, (int) result.get(0)); // assert sum is 0
    }
}
