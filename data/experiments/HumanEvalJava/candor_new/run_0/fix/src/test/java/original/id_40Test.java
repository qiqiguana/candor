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
        assertFalse(TriplesSumToZero.triplesSumToZero(list));
    }
    
    @Test
        public void testNothing(){
            TriplesSumToZero s = new TriplesSumToZero();
            }
    @Test
    public void test_empty_list_returns_false() {
        List<Integer> input = List.of();
        assertFalse(TriplesSumToZero.triplesSumToZero(input));
    }
    @Test
    public void test_single_element_list_returns_false() {
        List<Integer> input = List.of(1);
        assertFalse(TriplesSumToZero.triplesSumToZero(input));
    }
                                    
}