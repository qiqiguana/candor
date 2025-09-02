package original;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
* Test class of PairsSumToZero.
*/
class PairsSumToZeroTest {
    @Test
    void pairsSumToZero_Found() {
        List<Integer> list = List.of(2, 4, -5, 3, 5, 7);
        assertTrue(PairsSumToZero.pairsSumToZero(list));
    }
    
    @Test
        void testNothing(){
            PairsSumToZero s = new PairsSumToZero();
            }
    @Test
    public void testPairsSumToZero_SingleElementList_2() {
        List<Integer> input = Collections.singletonList(1);
        assertFalse(PairsSumToZero.pairsSumToZero(input));
    }
    @Test
    public void testPairsSumToZero_SingleElementList() {
        List<Integer> input = java.util.Collections.singletonList(1);
        assertFalse(PairsSumToZero.pairsSumToZero(input));
    }
    @Test
    public void testPairsSumToZero_DuplicateElementsSumToZero_Fixed() {
        List<Integer> input = Arrays.asList(2, -2);
        assertTrue(PairsSumToZero.pairsSumToZero(input));
    }
    @Test
    public void testPairsSumToZero_NegativeNumbers() {
        List<Integer> input = Arrays.asList(-1, -2, -3);
        assertFalse(PairsSumToZero.pairsSumToZero(input));
    }
                                    
}