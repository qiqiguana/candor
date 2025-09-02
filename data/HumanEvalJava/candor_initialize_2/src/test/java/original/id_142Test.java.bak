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
    void test_sumSquares_withEmptyList() {
        List<Object> lst = new ArrayList<>();
        int result = SumSquares1.sumSquares(lst);
        assertEquals(0, result);
    }
    
    @Test
        public void testNothing(){
            SumSquares1 s = new SumSquares1();
            }
    @Test
    public void testSumSquares1WithMultipleOf3Index() {
        java.util.List<java.lang.Object> lst = new java.util.ArrayList<>(java.util.Arrays.asList(1, 2, 3));
        int expected_result = 6;
        assertEquals(expected_result, original.SumSquares1.sumSquares(lst));
    }
    @Test
    public void testSumSquares1WithEmptyList() {
        List<Object> lst = new ArrayList<>();
        int expected_result = 0;
        assertEquals(expected_result, SumSquares1.sumSquares(lst));
    }
    @Test
    public void testSumSquares_MultipleOf4ButNot3_2() {
        List<Object> input = new ArrayList<>();
        input.add(1);
        input.add(2);
        input.add(3);
        input.add(16);
        assertEquals((int)Math.pow(16, 2) + 6, SumSquares1.sumSquares(input));
    }
                                    
}