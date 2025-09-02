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
}