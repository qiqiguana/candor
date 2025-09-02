package original;

import java.util.List;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
/**
* Test class of PairsSumToZero.
*/
class PairsSumToZeroTest {

@Test
void pairsSumToZero_TwoDistinctElementsThatSumToZero_ReturnsTrue() {
    List<Integer> list = List.of(2, 4, -5, 3, 5, 7);
    assertTrue(PairsSumToZero.pairsSumToZero(list));
}

@Test
    public void testNothing(){
        PairsSumToZero s = new PairsSumToZero();
        }
                                
}