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
        List<Integer> result = EvenOddCount.evenOddCount(12345);
        assertEquals(2, result.get(0));
    }
    
    @Test
        public void testNothing(){
            EvenOddCount s = new EvenOddCount();
            }
    @Test
    public void EvenOddCountTestCase1() {
        int num = 0;
        List<Integer> result = Arrays.asList(1, 0);
        assertEquals(result, original.EvenOddCount.evenOddCount(num));
    }
                                    
}