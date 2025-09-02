package original;

import java.util.Arrays;
import static java.util.List.of;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
/**
* Test class of IsSorted.
*/
class IsSortedTest {
    @Test
    void testIsSorted_SortedList_ReturnsTrue() {
        List<Object> sortedList = new ArrayList<>();
        sortedList.add(1);
        sortedList.add(2);
        sortedList.add(3);
        assertTrue(IsSorted.isSorted(sortedList));
    }
    
    @Test
        public void testNothing(){
            IsSorted s = new IsSorted();
            }
    @Test
    public void testEmptyList() {
        assertTrue(IsSorted.isSorted(new ArrayList<>()));
    }
    @Test
    public void testSingleElementList() {
        assertTrue(IsSorted.isSorted(List.of(1)));
    }
    @Test
    public void testDuplicateElements() {
        assertFalse(IsSorted.isSorted(java.util.Arrays.asList(1, 2, 2, 2, 3)));
    }
    @Test
    public void testUnsortedList1() {
        assertFalse(IsSorted.isSorted(Arrays.asList(3, 2, 1)));
    }
    @Test
    public void testEmptyListIsSorted() {
        assertTrue(IsSorted.isSorted(List.of()));
    }
    @Test
    public void testListWithDuplicatesButSorted1() {
        assertTrue(IsSorted.isSorted(List.of(1, 2, 2, 3, 4)));
    }
                                    
}