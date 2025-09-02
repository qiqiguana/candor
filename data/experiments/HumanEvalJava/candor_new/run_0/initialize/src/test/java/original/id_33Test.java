package original;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
* Test class of SortThird.
*/
class SortThirdTest {
    @Test
    void testSortThird_SortedElementsAtEveryThirdIndex() {
        List<Integer> l = new ArrayList<>(List.of(5, 8, -12, 4, 23, 2, 3, 11, 12, -10));
        List<Integer> expected = new ArrayList<>(List.of(-10, 8, -12, 3, 23, 2, 4, 11, 12, 5));
        assertEquals(expected, SortThird.sortThird(l));
    }
}