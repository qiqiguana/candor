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
    void testSortThird() {
        List<Integer> inputList = new ArrayList<>(List.of(5, 6, 3, 4, 8, 9, 2));
        List<Integer> expectedList = new ArrayList<>(List.of(2, 6, 3, 4, 8, 9, 5));
        assertEquals(expectedList, SortThird.sortThird(inputList));
    }
}