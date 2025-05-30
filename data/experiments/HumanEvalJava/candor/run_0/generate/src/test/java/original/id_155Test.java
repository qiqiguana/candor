package original;

import java.util.Arrays;

import java.util.List;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
/**
* Test class of EvenOddCount.
*/
class EvenOddCountTest {
    @Test
    void testEvenOddCount() {
        List<Integer> result = EvenOddCount.evenOddCount(123);
        assertEquals(1, result.get(0));
        assertEquals(2, result.get(1));
    }
    
    @Test
        public void testNothing(){
            EvenOddCount s = new EvenOddCount();
            }
    @Test
    public void test_evenOddCount_with_positive_number() {
        List<Integer> result = EvenOddCount.evenOddCount(123);
        assertEquals(Arrays.asList(1, 2), result);
    }
    @Test
    public void test_evenOddCount_with_negative_number() {
        List<Integer> result = EvenOddCount.evenOddCount(-123);
        assertEquals(Arrays.asList(1, 2), result);
    }
    @Test
    public void test_evenOddCount_with_zero() {
        List<Integer> result = EvenOddCount.evenOddCount(0);
        assertEquals(Arrays.asList(1, 0), result);
    }
    @Test
    public void test_evenOddCount_with_single_digit_number() {
        List<Integer> result = EvenOddCount.evenOddCount(7);
        assertEquals(Arrays.asList(0, 1), result);
    }
    @Test
    public void test_evenOddCount_with_number_having_only_even_digits() {
        List<Integer> result = EvenOddCount.evenOddCount(222);
        assertEquals(Arrays.asList(3, 0), result);
    }
    @Test
    public void test_evenOddCount_with_number_having_only_odd_digits() {
        List<Integer> result = EvenOddCount.evenOddCount(333);
        assertEquals(Arrays.asList(0, 3), result);
    }
                                    
}