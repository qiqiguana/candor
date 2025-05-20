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
}