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
    void testEvenOddCountForNegativeNumber() {
        List<Integer> expected = Arrays.asList(1, 1);
        assertEquals(expected, EvenOddCount.evenOddCount(-12));
    }
    
    @Test
        public void testNothing(){
            EvenOddCount s = new EvenOddCount();
            }
    @Test
    public void testEvenOddCountWithZeroInput() {
        Integer num = Integer.parseInt("0");
        List<Integer> result = EvenOddCount.evenOddCount(num);
        assertEquals(Arrays.asList(1, 0), result);
    }
                                    
}