package original;

import java.util.ArrayList;

import java.util.List;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
/**
* Test class of LargestSmallestIntegers.
*/
class LargestSmallestIntegersTest {

    @Test
    void testLargestSmallestIntegers_EmptyList_ReturnsNull() {
        List<Object> input = new ArrayList<>();
        List<Integer> result = LargestSmallestIntegers.largestSmallestIntegers(input);
        assertNotNull(result);
        assertEquals(2, result.size());
        assertNull(result.get(0));
        assertNull(result.get(1));
    }
    
    @Test
        public void testNothing(){
            LargestSmallestIntegers s = new LargestSmallestIntegers();
            }
    @Test
    public void testLargestSmallestIntegersWithNegativeNumbers() {
        List<Object> lst = new ArrayList<>();
        lst.add(-1);
        lst.add(-3);
        lst.add(-5);
        lst.add(-6);
        List<Integer> result = LargestSmallestIntegers.largestSmallestIntegers(lst);
        assertNotNull(result);
        assertEquals(-1, (int) result.get(0));
        assertNull(result.get(1));
    }
    @Test
    public void testLargestSmallestIntegersWithPositiveNumbers() {
        List<Object> lst = new ArrayList<>();
        lst.add(1);
        lst.add(3);
        lst.add(5);
        lst.add(6);
        List<Integer> result = LargestSmallestIntegers.largestSmallestIntegers(lst);
        assertNotNull(result);
        assertNull(result.get(0));
        assertEquals(1, (int) result.get(1));
    }
    @Test
    public void testLargestSmallestIntegersWithSingleZeroElement() {
        List<Object> lst = new ArrayList<>();
        lst.add(0);
        List<Integer> result = LargestSmallestIntegers.largestSmallestIntegers(lst);
        assertNotNull(result);
        assertNull(result.get(0));
        assertNull(result.get(1));
    }
                                    

}