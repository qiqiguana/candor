package original;

import java.util.Arrays;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
/**
* Test class of IsSorted.
*/
class IsSortedTest {
    @Test
    void testIsSorted_DuplicateElements_ReturnsFalse() {
        List<Object> lst = new ArrayList<>();
        lst.add(1);
        lst.add(2);
        lst.add(2);
        lst.add(2);
        lst.add(3);
        lst.add(4);
        assertFalse(IsSorted.isSorted(lst));
    }
    
    @Test
        public void testNothing(){
            IsSorted s = new IsSorted();
            }
    @Test
    public void isSorted_EmptyList_ReturnsTrue() {
        List<Object> input = new ArrayList<>();
        assertTrue(IsSorted.isSorted(input));
    }
    @Test
    public void isSorted_UnsortedList_ReturnsFalse() {
        List<Object> input = Arrays.asList(3, 2, 1);
        assertFalse(IsSorted.isSorted(input));
    }
    @Test
    public void isSorted_SortedListWithDuplicatesLessThanThree_ReturnsTrue() {
        List<Object> input = Arrays.asList(1, 2, 2, 3);
        assertTrue(IsSorted.isSorted(input));
    }
                                    
}