package original;

import java.util.List;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
* Test class of TriplesSumToZero.
*/
class TriplesSumToZeroTest {
    @Test
    void test_triplesSumToZero_with_triplets_sum_to_zero() {
        List<Integer> list = List.of(1, 3, -2, 1);
        assertTrue(TriplesSumToZero.triplesSumToZero(list));
    }
}