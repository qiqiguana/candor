package original;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
/**
* Test class of SortedListSum.
*/
class SortedListSumTest {
    @Test
    void testSortedListSum() {
        List<String> input = new ArrayList<>();
        input.add("aa");
        input.add("a");
        input.add("aaa");
        List<Object> result = SortedListSum.sortedListSum(input);
        assertEquals(1, result.size());
    }
}
