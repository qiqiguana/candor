package original;

import java.util.*;
import java.util.List;
import java.util.ArrayList;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
/**
* Test class of Unique.
*/
class UniqueTest {
    @Test
    void testUnique_shouldReturnSortedUniqueElements() {
        List<Integer> input = new ArrayList<>(Arrays.asList(5, 3, 5, 2, 3, 3, 9, 0, 123));
        List<Integer> expected = new ArrayList<>(Arrays.asList(0, 2, 3, 5, 9, 123));
        assertEquals(expected, Unique.unique(input));
    }
}
