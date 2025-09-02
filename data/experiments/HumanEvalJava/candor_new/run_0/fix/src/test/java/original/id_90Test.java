package original;

import static org.junit.jupiter.api.Assertions.assertEquals;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
/**
* Test class of NextSmallest.
*/
class NextSmallestTest {
    @Test
    void test_next_smallest_with_unique_elements() {
        List<Object> numbers = List.of(1, 2, 3, 4, 5);
        Integer result = NextSmallest.nextSmallest(numbers);
        assertEquals(Integer.valueOf(2), result);
    }
    
    @Test
        public void testNothing(){
            NextSmallest s = new NextSmallest();
            }
    @Test
    void testNextSmallestWithEmptyAndSingletonList() {
        List<Object> input = new ArrayList<>();
        assertNull(NextSmallest.nextSmallest(input));
    
        input.add(1);
        assertNull(NextSmallest.nextSmallest(input));
    }
    @Test
    public void testNextSmallestMultipleElementsDescendingOrder() {
        List<Object> lst = List.of(5, 4, 3, 2, 1);
        Integer result = NextSmallest.nextSmallest(lst);
        assertEquals(2, result);
    }
    @Test
    public void testNextSmallestDuplicateElements() {
        List<Object> lst = List.of(5, 4, 3, 2, 1, 1);
        Integer result = NextSmallest.nextSmallest(lst);
        assertEquals(1, result);
    }
                                    
}