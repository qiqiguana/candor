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
    void testStrangeSortList_SortedList() {
        List<Object> input = new ArrayList<>(List.of(5, 6, 7, 8, 9));
        List<Object> expected = new ArrayList<>(List.of(5, 9, 6, 8, 7));
        assertEquals(expected, StrangeSortList.strangeSortList(input));
    }
    
    @Test
        public void testNothing(){
            StrangeSortList s = new StrangeSortList();
            }
    @Test
    public void testStrangeSortListWithNonIntegerValues() {
        List<Object> input = new ArrayList<>();
        input.add(1);
        input.add("a");
        input.add(3);
        input.add(null);
        input.add(5);
        List<Object> expected = new ArrayList<>();
        expected.add(1);
        expected.add(5);
        expected.add(3);
        assertEquals(expected, StrangeSortList.strangeSortList(input));
    }
                                    
}