package original;

import java.util.Arrays;
import java.util.List;
import java.util.ArrayList;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
/**
* Test class of TriplesSumToZero.
*/
class TriplesSumToZeroTest {
    @Test
    void testTriplesSumToZero() {
        List<Integer> list = new ArrayList<>();
        list.add(1);
        list.add(3);
        list.add(-2);
        list.add(1);
        assertTrue(TriplesSumToZero.triplesSumToZero(list));
    }
    
    @Test
        public void testNothing(){
            TriplesSumToZero s = new TriplesSumToZero();
            }
    @Test
    public void testTriplesSumToZeroInitialization() {
        assertDoesNotThrow(() -> new original.TriplesSumToZero());
    }
    @Test
    public void testNoTripletsSumToZero() {
        List<Integer> input = Arrays.asList(1, 2, 3, 4);
        assertFalse(original.TriplesSumToZero.triplesSumToZero(input));
    }
                                    
}