package original;

import java.util.List;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
/**
* Test class of TriplesSumToZero.
*/
class TriplesSumToZeroTest {
    @Test
    void test_TriplesSumToZero_TwoPositivesAndOneNegative_ReturnsTrue() {
        List<Integer> l = List.of(1, 3, -2, 1);
        assertTrue(TriplesSumToZero.triplesSumToZero(l));
    }
}