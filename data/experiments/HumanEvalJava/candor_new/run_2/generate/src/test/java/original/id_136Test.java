package original;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import java.util.ArrayList;

import java.util.List;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
/**
* Test class of LargestSmallestIntegers.
*/
class LargestSmallestIntegersTest {

    @Test
    void testLargestSmallestIntegers_EmptyList_ReturnsNullValues() {
        List<Object> input = new ArrayList<>();
        List<Integer> expected = new ArrayList<>();
        expected.add(null);
        expected.add(null);
        assertEquals(expected, LargestSmallestIntegers.largestSmallestIntegers(input));
    }
    
    @Test
        public void testNothing(){
            LargestSmallestIntegers s = new LargestSmallestIntegers();
            }
    @Test
    public void testLargestSmallestIntegersPositiveNumbersOnly() {
        List<Object> input = new ArrayList<>();
        input.add(2);
        input.add(4);
        input.add(1);
        input.add(3);
        input.add(5);
        input.add(7);
        List<Integer> expected = new ArrayList<>();
        expected.add(null);
        expected.add(1);
        assertArrayEquals(expected.toArray(), LargestSmallestIntegers.largestSmallestIntegers(input).toArray());
    }
    @Test
    public void testLargestSmallestIntegersNegativeNumbersOnly() {
        List<Object> input = new ArrayList<>();
        input.add(-1);
        input.add(-3);
        input.add(-5);
        input.add(-6);
        List<Integer> expected = new ArrayList<>();
        expected.add(-1);
        expected.add(null);
        assertArrayEquals(expected.toArray(), LargestSmallestIntegers.largestSmallestIntegers(input).toArray());
    }
    @Test
    public void testLargestSmallestIntegersWithZero1() {
        List<Object> input = new ArrayList<>();
        input.add(2);
        input.add(0);
        input.add(-1);
        List<Integer> expected = new ArrayList<>();
        expected.add(-1);
        expected.add(2);
        assertArrayEquals(expected.toArray(), LargestSmallestIntegers.largestSmallestIntegers(input).toArray());
    }
                                    
}