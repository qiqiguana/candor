package original;

import java.util.Collections;
import java.util.Arrays;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
/**
* Test class of MaxElement.
*/
class MaxElementTest {
    @Test
    void testMaxElement_ReturnsMaximumValue_WhenListHasMultipleElements() {
        List<Integer> list = new ArrayList<>();
        list.add(1);
        list.add(2);
        list.add(3);
        assertEquals(3, MaxElement.maxElement(list));
    }
    
    @Test
        public void testNothing(){
            MaxElement s = new MaxElement();
            }
    @Test
    void testMaxElement_with_empty_list() {
        List<Integer> l = new ArrayList<>();
        assertEquals(Integer.MIN_VALUE, MaxElement.maxElement(l));
    }
    @Test
    void testMaxElement_with_single_element_list_1() {
        List<Integer> l = Arrays.asList(5);
        int expected = Collections.max(l);
        assertEquals(expected, MaxElement.maxElement(l));
    }
    @Test
    void testMaxElement_with_multiple_elements_list() {
        List<Integer> l = Arrays.asList(1, 2, 3, 4, 5);
        assertEquals(5, MaxElement.maxElement(l));
    }
    @Test
    void testMaxElement_with_negative_numbers_list() {
        List<Integer> l = Arrays.asList(-1, -2, -3, -4, -5);
        assertEquals(-1, MaxElement.maxElement(l));
    }
                                    
}