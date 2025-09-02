package original;

import java.util.List;
import java.util.ArrayList;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
/**
* Test class of PairsSumToZero.
*/
class PairsSumToZeroTest {
    @Test
    void pairsSumToZero_ListWithElementsThatSumToZero_ReturnsTrue() {
        List<Integer> list = new ArrayList<>(List.of(2, 4, -5, 3, 5, 7));
        assertTrue(PairsSumToZero.pairsSumToZero(list));
    }
}