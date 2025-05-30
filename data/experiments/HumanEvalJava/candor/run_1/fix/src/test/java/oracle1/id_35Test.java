package oracle1;

import java.util.List;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
* Test class of MaxElement.
*/
class MaxElementTest {

    @Test
    void testMaxElement_ListWithPositiveNumbers_ReturnsMaximumNumber() {
        List<Integer> numbers = List.of(1, 2, 3);
        int max = MaxElement.maxElement(numbers);
        assertEquals(3, max);
    }
    
    @Test
        void testNothing(){
            MaxElement s = new MaxElement();
            }
    @Test
    public void testSingleElementList() {
        assertEquals(5, MaxElement.maxElement(List.of(5)));
    }
    @Test
    public void testMultipleElementsList() {
        assertEquals(5, MaxElement.maxElement(List.of(1, 2, 3, 4, 5)));
    }
    @Test
    public void testNegativeNumbersList() {
        assertEquals(2, MaxElement.maxElement(List.of(-5, -3, -1, 0, 2)));
    }
    @Test
    public void testDuplicateMaximumElementsList() {
        assertEquals(5, MaxElement.maxElement(List.of(1, 2, 3, 4, 5, 5)));
    }
    @Test
    public void testMinValueList() {
        assertEquals(Integer.MIN_VALUE, MaxElement.maxElement(List.of(Integer.MIN_VALUE, Integer.MIN_VALUE)));
    }
                                    
}