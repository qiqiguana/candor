package original;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
* Test class of StrangeSortList.
*/
class StrangeSortListTest {

    @Test
    void testStrangeSortList_EmptyList_ReturnsEmptyList() {
        List<Object> input = new ArrayList<>();
        List<Object> expected = new ArrayList<>();
        assertEquals(expected, StrangeSortList.strangeSortList(input));
    }
    
    @Test
        public void testNothing(){
            StrangeSortList s = new StrangeSortList();
            }
    @Test
    public void TestStrangeSortList_EmptyList() {
        List<Object> input = new ArrayList<>();
        List<Object> expected = Collections.emptyList();
        assertEquals(expected, StrangeSortList.strangeSortList(input));
    }
    @Test
    public void TestStrangeSortList_NullInput() {
        List<Object> input = null;
        assertThrows(NullPointerException.class, () -> StrangeSortList.strangeSortList(input));
    }
    @Test
    void testStrangeSortListWithEmptyInput() {
        List<Object> input = new ArrayList<>();
        List<Object> expected = new ArrayList<>();
        assertEquals(expected, StrangeSortList.strangeSortList(input));
    }
    @Test
    public void testStrangeSortListWithEmptyList() {
        List<Object> input = new ArrayList<>();
        assertEquals(Collections.emptyList(), StrangeSortList.strangeSortList(input));
    }
                                    
}