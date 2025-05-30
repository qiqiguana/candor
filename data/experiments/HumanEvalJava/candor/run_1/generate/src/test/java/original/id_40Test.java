package original;

import java.util.Arrays;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
/**
* Test class of TriplesSumToZero.
*/
class TriplesSumToZeroTest {
    @Test
    void test_triplesSumToZero_EmptyList_False() {
        List<Integer> l = List.of();
        assertFalse(TriplesSumToZero.triplesSumToZero(l));
    }
    
    @Test
        public void testNothing(){
            TriplesSumToZero s = new TriplesSumToZero();
            }
    @Test
    public void testTriplesSumToZero_8() {
        List<Integer> input = null;
        assertThrows(NullPointerException.class, () -> TriplesSumToZero.triplesSumToZero(input));
    }
    @Test
    public void testTriplesSumToZero_1() {
        List<Integer> input = new ArrayList<>();
        assertFalse(TriplesSumToZero.triplesSumToZero(input));
    }
    @Test
    public void testTriplesSumToZero_2() {
        List<Integer> input = Arrays.asList(1);
        assertFalse(TriplesSumToZero.triplesSumToZero(input));
    }
    @Test
    public void testTriplesSumToZero_3() {
        List<Integer> input = Arrays.asList(1, 2);
        assertFalse(TriplesSumToZero.triplesSumToZero(input));
    }
    @Test
    public void testTriplesSumToZero_4() {
        List<Integer> input = Arrays.asList(-2, 0, 2);
        assertTrue(TriplesSumToZero.triplesSumToZero(input));
    }
    @Test
    public void testTriplesSumToZero_5() {
        List<Integer> input = Arrays.asList(1, 2, 3);
        assertFalse(TriplesSumToZero.triplesSumToZero(input));
    }
    @Test
    public void testTriplesSumToZero_7() {
        List<Integer> input = Arrays.asList(0, 0, 0);
        assertTrue(TriplesSumToZero.triplesSumToZero(input));
    }
    @Test
    public void testTriplesSumToZero_9() {
        List<Integer> input = new ArrayList<>();
        for (int i = 0; i < 10000; i++) {
            input.add(i);
        }
        assertFalse(TriplesSumToZero.triplesSumToZero(input));
    }
                                    
}