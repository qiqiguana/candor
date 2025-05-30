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
    void testEvenOddCount_withSingleDigit_evenAndOddCountsAreCorrect() {
        List<Integer> result = EvenOddCount.evenOddCount(7);
        assertEquals(Arrays.asList(0, 1), result);
    }
    
    @Test
        void testNothing(){
            EvenOddCount s = new EvenOddCount();
            }
    @Test
    public void testEvenOddCountPositive() {
        List<Integer> result = EvenOddCount.evenOddCount(12345);
        assertEquals(Arrays.asList(2, 3), result);
    }
    @Test
    public void testEvenOddCountNegative() {
        List<Integer> result = EvenOddCount.evenOddCount(-12345);
        assertEquals(Arrays.asList(2, 3), result);
    }
    @Test
    public void testEvenOddCountSingleDigit() {
        List<Integer> result = EvenOddCount.evenOddCount(7);
        assertEquals(Arrays.asList(0, 1), result);
    }
    @Test
    public void testEvenOddCountZero() {
        List<Integer> result = EvenOddCount.evenOddCount(0);
        assertEquals(Arrays.asList(1, 0), result);
    }
    @Test
    public void testEvenOddCountLargeNumberFixed2() {
        long largeNum = 1234567890L;
        List<Integer> result = EvenOddCount.evenOddCount((int) largeNum);
        assertEquals(Arrays.asList(5, 5), result);
    }
                                    
}