package original;

import java.util.ArrayList;

import java.util.List;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
/**
* Test class of F.
*/
class FTest {
    @Test
    void test_f_with_even_and_odd_numbers() {
        List<Integer> result = F.f(5);
        assertEquals(1, result.get(0));
    }
    
    @Test
        void testNothing(){
            F s = new F();
            }
    @Test
    public void test_f_1() {
    	List<Integer> result = F.f(1);
    	assertEquals(List.of(1), result);
    }
    @Test
    public void test_f_5() {
    	List<Integer> result = F.f(5);
    	assertEquals(List.of(1, 2, 6, 24, 15), result);
    }
    @Test
    public void test_f_0() {
    	List<Integer> result = F.f(0);
    	assertEquals(List.of(), result);
    }
    @Test
    public void test_f_even_odd() {
        List<Integer> result = F.f(5);
        assertEquals(5, result.size());
        assertEquals(1, (int)result.get(0));
        assertEquals(2, (int)result.get(1));
        assertEquals(6, (int)result.get(2));
        assertEquals(24, (int)result.get(3));
        assertEquals(15, (int)result.get(4));
    }
                                    
}