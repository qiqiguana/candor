package original;

import java.util.List;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
/**
* Test class of TriplesSumToZero.
*/
class TriplesSumToZeroTest {
    @Test
    void testTriplesSumToZero_DistinctElementsSumToZero_ReturnsTrue() {
        List<Integer> list = List.of(1, 3, -2, 1);
        assertTrue(TriplesSumToZero.triplesSumToZero(list));
    }
}