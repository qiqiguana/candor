package original;

import java.util.Arrays;
import java.util.ArrayList;

import java.util.Collections;

import java.util.List;


import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
/**
* Test class of SortEven.
*/
class SortEvenTest {
    @Test
    void testSortEven_EvenIndicesSorted() {
        List<Integer> l = new ArrayList<>(List.of(5, 3, -5, 2, -3, 3, 9, 0, 123, 1, -10));
        List<Integer> expected = List.of(-10, 3, -5, 2, -3, 3, 5, 0, 9, 1, 123);
        assertEquals(expected, SortEven.sortEven(l));
    }
    
    @Test
        public void testNothing(){
            SortEven s = new SortEven();
            }
    @Test
    public void testSortEven_EmptyList() {
        List<Integer> input = new ArrayList<>();
        List<Integer> expectedOutput = new ArrayList<>();
        assertEquals(expectedOutput, SortEven.sortEven(input));
    }
    @Test
    public void testSortEven_EvenIndicesSorted_2() {
        List<Integer> input = Arrays.asList(5, 6, 3, 4);
        List<Integer> expectedOutput = Arrays.asList(3, 6, 5, 4);
        assertEquals(expectedOutput, SortEven.sortEven(input));
    }
                                    
}