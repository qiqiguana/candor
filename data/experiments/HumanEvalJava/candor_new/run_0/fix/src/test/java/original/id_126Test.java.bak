package original;

import java.util.Arrays;
import static org.junit.jupiter.api.Assertions.assertFalse;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
/**
* Test class of IsSorted.
*/
class IsSortedTest {
    @Test
    void testIsSorted_DuplicateMoreThanTwice_ReturnsFalse() {
        List<Object> list = new ArrayList<>();
        list.add(1);
        list.add(2);
        list.add(2);
        list.add(2);
        list.add(3);
        assertFalse(IsSorted.isSorted(list));
    }
    
    @Test
        public void testNothing(){
            IsSorted s = new IsSorted();
            }
    @Test
    public void testIsSorted_SingleElementList() {
        java.util.List<java.lang.Object> lst = new java.util.ArrayList<>(java.util.Arrays.asList(1));
        assertTrue(IsSorted.isSorted(lst));
    }
    @Test public void testIsSorted_AlreadySortedListWithDuplicates_1() { java.util.List<java.lang.Object> lst = new java.util.ArrayList<>(java.util.Arrays.asList(1, 2, 2, 3, 4, 5)); assertTrue(IsSorted.isSorted(lst)); }
    @Test
    public void isSorted_unsorted_list_with_single_duplicate_8() {
        List<Object> lst = Arrays.asList(5, 2, 8, 12, 3);
        boolean result = IsSorted.isSorted(lst);
        assertFalse(result);
    }
                                    
}