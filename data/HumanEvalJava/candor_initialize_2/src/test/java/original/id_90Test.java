package original;

import java.util.List;
import java.util.ArrayList;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
/**
* Test class of NextSmallest.
*/
class NextSmallestTest {

    @Test
    void testNextSmallest_WithMultipleElements_ReturnsSecondSmallest() {
        List<Object> list = new ArrayList<>();
        list.add(5);
        list.add(1);
        list.add(4);
        list.add(3);
        list.add(2);
        Integer result = NextSmallest.nextSmallest(list);
        assertEquals(2, result);
    }
    
    @Test
        public void testNothing(){
            NextSmallest s = new NextSmallest();
            }
    @Test
    public void testNextSmallestWithEmptyList() {
        List<Object> lst = new ArrayList<>();
        assertNull(NextSmallest.nextSmallest(lst));
    }
    @Test
    public void testNextSmallestWithSingleElementList() {
        assertNull(NextSmallest.nextSmallest(List.of(5)));
    }
    @Test
    public void testNextSmallestWithDuplicateElements() {
        assertEquals(2, (int) NextSmallest.nextSmallest(List.of(1, 2, 3, 4, 4, 5)));
    }
    @Test
    public void testNextSmallestWithNegativeNumbers() {
        assertEquals(-34, (int) NextSmallest.nextSmallest(List.of(-35, -34, -33, -32, -31)));
    }
    @Test
    public void testNextSmallestWithUnorderedList() {
        assertEquals(2, (int) NextSmallest.nextSmallest(List.of(5, 1, 4, 3, 2)));
    }
    @Test
    public void testNextSmallestWithEmptyListUpdated() {
        assertNull(NextSmallest.nextSmallest(java.util.Collections.emptyList()));
    }
                                    
}