package original;

import static org.junit.jupiter.api.Assertions.assertEquals;
import java.util.Arrays;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
/**
* Test class of LargestSmallestIntegers.
*/
class LargestSmallestIntegersTest {
    @Test
    void testLargestSmallestIntegers_withOnlyPositiveNumbers() {
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

        assertEquals(expected, LargestSmallestIntegers.largestSmallestIntegers(input));
    }
    
    @Test
        public void testNothing(){
            LargestSmallestIntegers s = new LargestSmallestIntegers();
            }
    @Test
    public void testLargestSmallestIntegersWithNegativeAndPositive() {
        List<Object> input = new ArrayList<>(Arrays.asList(-3, 1));
        List<Integer> expectedOutput = new ArrayList<>(Arrays.asList(-3, 1));
        assertEquals(expectedOutput, LargestSmallestIntegers.largestSmallestIntegers(input));
    }
    @Test
    public void testEmptyList1() {
        List<Object> input = new ArrayList<>();
        List<Integer> expectedOutput = new ArrayList<>(Arrays.asList(null, null));
        assertEquals(expectedOutput, LargestSmallestIntegers.largestSmallestIntegers(input));
    }
    @Test
    public void testListWithOnlyZeros() {
        List<Object> input = new ArrayList<>(Arrays.asList(0));
        List<Integer> expectedOutput = new ArrayList<>(Arrays.asList(null, null));
        assertEquals(expectedOutput, LargestSmallestIntegers.largestSmallestIntegers(input));
    }
    @Test
    public void testMultipleLargestNegativeIntegers1() {
        List<Object> input = new ArrayList<>(Arrays.asList(-6, -4, -4, -3, 1));
        List<Integer> expectedOutput = new ArrayList<>(Arrays.asList(-3, 1));
        assertEquals(expectedOutput, LargestSmallestIntegers.largestSmallestIntegers(input));
    }
    @Test
    public void testMultipleSmallestPositiveIntegers1() {
        List<Object> input = new ArrayList<>(Arrays.asList(-3, 1, 1, 2));
        List<Integer> expectedOutput = new ArrayList<>();
        expectedOutput.add(-3);
        expectedOutput.add(1);
        assertEquals(expectedOutput, LargestSmallestIntegers.largestSmallestIntegers(input));
    }
    @Test
    public void testNoLargestNegativeInteger1() {
        List<Object> input = new ArrayList<>(Arrays.asList(1, 2, 3));
        List<Integer> expectedOutput = new ArrayList<>(Arrays.asList(null, 1));
        assertEquals(expectedOutput, LargestSmallestIntegers.largestSmallestIntegers(input));
    }
    @Test
    public void testNoPositiveInteger() {
        List<Object> input = new ArrayList<>(Arrays.asList(-1, -2, -3));
        LargestSmallestIntegers originalClass = new LargestSmallestIntegers();
        List<Integer> expectedOutput = new ArrayList<>(Arrays.asList(-1, null));
        assertEquals(expectedOutput, originalClass.largestSmallestIntegers(input));
    }
                                    
}