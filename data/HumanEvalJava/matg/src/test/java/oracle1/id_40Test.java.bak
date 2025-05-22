package oracle1;

import java.util.List;
import java.util.ArrayList;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
/**
* Test class of TriplesSumToZero.
*/
class TriplesSumToZeroTest {
    @Test
    void testTriplesSumToZeroTrue() {
        List<Integer> list = new ArrayList<>();
        list.add(1);
        list.add(3);
        list.add(-2);
        list.add(1);
        assertTrue(TriplesSumToZero.triplesSumToZero(list));
    }
    
    @Test
        void testNothing(){
            TriplesSumToZero s = new TriplesSumToZero();
            }
    @Test
    public void Test_TriplesSumToZero_HappyPath() {
        List<Integer> input = List.of(1, 3, -2, 1);
        assertTrue(TriplesSumToZero.triplesSumToZero(input));
    }
    @Test
    public void Test_TriplesSumToZero_SadPath() {
        List<Integer> input = List.of(1, 3, 5, 0);
        assertFalse(TriplesSumToZero.triplesSumToZero(input));
    }
    @Test
    public void Test_TriplesSumToZero_EmptyList() {
        List<Integer> input = List.of();
        assertFalse(TriplesSumToZero.triplesSumToZero(input));
    }
    @Test
    public void Test_TriplesSumToZero_SingleElement() {
        List<Integer> input = List.of(1);
        assertFalse(TriplesSumToZero.triplesSumToZero(input));
    }
    @Test
    public void Test_TriplesSumToZero_DuplicateElements() {
        List<Integer> input = List.of(0, 0, 0);
        assertTrue(TriplesSumToZero.triplesSumToZero(input));
    }
    @Test
    public void Test_TriplesSumToZero_Id_1() {
        List<Integer> input = List.of(-1, 0, 1);
        assertTrue(TriplesSumToZero.triplesSumToZero(input));
    }
                                    
}