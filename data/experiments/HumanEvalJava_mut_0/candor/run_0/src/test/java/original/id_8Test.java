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
    void testSumProduct_withNonIntegerValues() {
        List<Object> numbers = new ArrayList<>();
        numbers.add(5);
        numbers.add(10);
        assertThrows(IllegalArgumentException.class, () -> SumProduct.sumProduct(numbers));
    }
}