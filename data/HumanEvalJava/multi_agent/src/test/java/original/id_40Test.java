package original;

import java.util.List;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;
/**
* Test class of TriplesSumToZero.
*/
class TriplesSumToZeroTest {
    @Test
    void testTriplesSumToZero_ReturnsFalse_WhenLessThanThreeDistinctElements() {
        List<Integer> list = List.of(1, 2);
        boolean result = TriplesSumToZero.triplesSumToZero(list);
        assertFalse(result);
    }
}