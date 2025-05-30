package original;

import java.util.List;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
* Test class of MaxElement.
*/
class MaxElementTest {
    @Test
    void testMaxElement_ReturnsMaximumValueInList() {
        List<Integer> list = List.of(5, 3, -5, 2, -3, 3, 9, 0, 123, 1, -10);
        int expectedMax = 123;
        assertEquals(expectedMax, MaxElement.maxElement(list));
    }
    
    @Test
        public void testNothing(){
            MaxElement s = new MaxElement();
            }
    @Test
    public void testMaxElementWithPositiveNumbers() {
        List<Integer> input = List.of(1, 2, 3);
        int expected = 3;
        assertEquals(expected, MaxElement.maxElement(input));
    }
    @Test
    public void testMaxElementWithNegativeNumbers() {
        List<Integer> input = List.of(-5, -3, -1);
        int expected = -1;
        assertEquals(expected, MaxElement.maxElement(input));
    }
    @Test
    public void testMaxElementWithMixedNumbers() {
        List<Integer> input = List.of(5, 3, -5, 2, -3, 3, 9, 0);
        int expected = 9;
        assertEquals(expected, MaxElement.maxElement(input));
    }
    @Test
    public void testMaxElementWithDuplicateMaximumValues() {
        List<Integer> input = List.of(5, 3, -5, 2, -3, 3, 9, 0, 9);
        int expected = 9;
        assertEquals(expected, MaxElement.maxElement(input));
    }
    @Test
    public void testMaxElementWithNullInput() {
        List<Integer> input = null;
        assertThrows(NullPointerException.class, () -> MaxElement.maxElement(input));
    }
                                    
}