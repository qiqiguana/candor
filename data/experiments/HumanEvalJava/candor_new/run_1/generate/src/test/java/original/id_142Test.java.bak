package original;

import static org.junit.jupiter.api.Assertions.assertEquals;
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
        int result = SumSquares1.sumSquares(list);
        assertEquals(0, result);
    }
    
    @Test
        public void testNothing(){
            SumSquares1 s = new SumSquares1();
            }
    @Test
    void testSumSquares1_withMultiplesOf4ButNot32() {
        List<Object> lst = new ArrayList<>();
        lst.add(64);
        int result = SumSquares1.sumSquares(lst);
        assertEquals(4096, result);
    }
    @Test
    void testSumSquares1_withNoMultiplesOf3Or4() {
        List<Object> lst = new ArrayList<>();
        lst.add(1);
        lst.add(2);
        int result = SumSquares1.sumSquares(lst);
        assertEquals(3, result);
    }
                                    
}