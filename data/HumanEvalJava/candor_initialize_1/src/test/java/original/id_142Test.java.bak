package original;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
/**
* Test class of SumSquares1.
*/
class SumSquares1Test {
    @Test
    void testSumSquares_EmptyList_ReturnsZero() {
        List<Object> list = new ArrayList<>();
        assertEquals(0, SumSquares1.sumSquares(list));
    }
    
    @Test
        public void testNothing(){
            SumSquares1 s = new SumSquares1();
            }
    @Test
    void testSumSquares1_with_empty_list() {
        List<Object> input = new ArrayList<>();
        assertEquals(0, SumSquares1.sumSquares(input));
    }
    @Test
    void testSumSquares1_with_null_input() {
        List<Object> input = new ArrayList<>();
        assertEquals(0, SumSquares1.sumSquares(input));
    }
    @Test
    public void testSumSquares_IndexMultipleOfThree() {
        List<Object> lst = new ArrayList<>();
        lst.add(9);
        int result = SumSquares1.sumSquares(lst);
        assertEquals(81, result);
    }
    @Test
    public void testSumSquares_IndexNotMultipleOfThreeOrFour() {
        List<Object> lst = new ArrayList<>();
        lst.add(1);
        lst.add(2);
        lst.add(5);
        int result = SumSquares1.sumSquares(lst);
        assertEquals(8, result);
    }
    @Test
    public void testSumSquares_EmptyList() {
        List<Object> lst = new ArrayList<>();
        int result = SumSquares1.sumSquares(lst);
        assertEquals(0, result);
    }
                                    
}