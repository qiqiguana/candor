package original;

import java.util.List;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;
import java.util.ArrayList;
/**
* Test class of Add1.
*/
class Add1Test {
    @Test
    void test_add_sum_of_even_numbers_at_odd_indices() {
        List<Integer> numbers = new ArrayList<>();
        numbers.add(4);
        numbers.add(88);
        assertEquals(88, Add1.add(numbers));
    }
}