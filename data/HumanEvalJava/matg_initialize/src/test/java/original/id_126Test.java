package original;

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
}