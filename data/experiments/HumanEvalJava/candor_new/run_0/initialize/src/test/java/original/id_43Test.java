package original;

import java.util.List;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
/**
* Test class of PairsSumToZero.
*/
class PairsSumToZeroTest {

    @Test
    void pairsSumToZero_EmptyList_ReturnsFalse() {
        List<Integer> list = List.of();
        assertFalse(PairsSumToZero.pairsSumToZero(list));
    }
}
