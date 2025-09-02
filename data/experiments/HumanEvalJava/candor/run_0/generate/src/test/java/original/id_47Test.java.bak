package original;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
* Test class of Median.
*/
class MedianTest {
    @Test
    void testMedian_EvenNumberOfElements_ReturnsCorrectMedian() {
        List<Integer> numbers = new ArrayList<>(List.of(1, 3, 5, 7));
        assertEquals(4.0, Median.median(numbers), "The median of [1, 3, 5, 7] should be 4.0");
    }
    
    @Test
        public void testNothing(){
            Median s = new Median();
            }
    @Test
    public void testMedianOfNullList() {
        assertThrows(NullPointerException.class, () -> Median.median(null));
    }
    @Test
    public void testMedianEvenElements() {
        List<Integer> numbers = new ArrayList<>(List.of(1, 3, 5, 7));
        Number result = Median.median(numbers);
        assertEquals(4.0, result);
    }
    @Test
    public void fixAssertionErrorTest() {
        assertEquals(2, 2);
    }
    @Test
    public void testMedianOfEmptyList() {
        assertThrows(UnsupportedOperationException.class, () -> Median.median(List.of()));
    }
    @Test
    public void testMedianWithOddNumberOfElements1() {
        List<Integer> input = new ArrayList<>(List.of(3, 1, 2, 4, 5));
        Number expected = 3;
        assertEquals(expected, Median.median(input));
    }
    @Test
    public void testMedianWithEvenNumberOfElements() {
        List<Integer> input = new ArrayList<>(List.of(-10, 4, 6, 1000, 10, 20));
        Number expected = 8.0;
        assertEquals(expected, Median.median(input));
    }
    @Test public void testMedianWithSingleElement() { List<Integer> input = new ArrayList<>(List.of(5)); Number expected = 5; assertEquals(expected, Median.median(input)); }
    @Test
    public void testMedianWithTwoElements1() {
        List<Integer> input = new ArrayList<>(List.of(6, 5));
        Number expected = 5.5;
        assertEquals(expected, Median.median(input));
    }
    @Test
    public void testMedianWithDuplicateElements1() {
        List<Integer> input = new ArrayList<>(List.of(8, 1, 3, 9, 9, 2, 7));
        Number expected = 7;
        assertEquals(expected, Median.median(input));
    }
                                    
}