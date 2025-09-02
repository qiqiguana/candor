package original;

import java.util.Arrays;
import static org.junit.jupiter.api.Assertions.assertEquals;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
/**
* Test class of SortArray1.
*/
class SortArray1Test {
    @Test
    void testSortArray_SumIsOdd_ReturnsSortedInAscendingOrder() {
        List<Object> array = new ArrayList<>();
        array.add(2);
        array.add(4);
        array.add(3);
        array.add(0);
        array.add(1);
        array.add(5);

        List<Object> expected = new ArrayList<>();
        expected.add(0);
        expected.add(1);
        expected.add(2);
        expected.add(3);
        expected.add(4);
        expected.add(5);

        assertEquals(expected, SortArray1.sortArray(array));
    }
    
    @Test
        public void testNothing(){
            SortArray1 s = new SortArray1();
            }
    @Test
    public void testSortArray1_with_empty_array() {
        List<Object> result = SortArray1.sortArray(new ArrayList<>());
        assertEquals(Collections.emptyList(), result);
    }
    @Test
    public void sortArray_withOddSumOfFirstAndLastElements_ascendingOrder() {
        List<Object> input = new ArrayList<>(List.of(2, 1));
        List<Object> expectedOutput = new ArrayList<>(List.of(1, 2));
        assertEquals(expectedOutput, SortArray.sortArray(input));
    }
    @Test
    public void Sort_array_with_even_sum_1() {
        List<Object> input = new ArrayList<>(Arrays.asList(2, 4, 3, 0, 1, 5, 6));
        List<Object> expectedOutput = new ArrayList<>(Arrays.asList(6, 5, 4, 3, 2, 1, 0));
        assertEquals(expectedOutput, SortArray1.sortArray(input));
    }
                                    
}