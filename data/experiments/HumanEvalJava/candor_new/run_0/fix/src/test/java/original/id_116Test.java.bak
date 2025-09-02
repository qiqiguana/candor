package original;

import java.util.ArrayList;

import java.util.Collections;

import java.util.Comparator;

import java.util.List;


import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
/**
* Test class of SortArray.
*/
class SortArrayTest {
    @Test
    void testSortArray_1() {
        List<Integer> input = new ArrayList<>(List.of(5, 2, 8, 1, 9));
        List<Integer> expected = new ArrayList<>(List.of(1, 2, 5, 8, 9));
        Collections.sort(input);
        assertEquals(expected, input);
    }
    
    @Test
        public void testNothing(){
            SortArray s = new SortArray();
            }
    @Test
    public void testSortArrayWithEmptyList() {
        List<Object> input = new ArrayList<>();
        List<Object> result = SortArray.sortArray(input);
        assertTrue(result.isEmpty());
    }
    @Test
    public void testSortArrayWithSingleElementList() {
        List<Object> input = new ArrayList<>();
        input.add(1);
        List<Object> result = SortArray.sortArray(input);
        assertEquals(1, result.size());
        assertEquals(1, result.get(0));
    }
    @Test
    public void testSortArrayWithDuplicateElementsList() {
        List<Object> input = new ArrayList<>();
        input.add(2);
        input.add(4);
        input.add(8);
        input.add(16);
        input.add(32);
        List<Object> result = SortArray.sortArray(input);
        assertEquals(5, result.size());
        assertEquals(2, result.get(0));
        assertEquals(4, result.get(1));
        assertEquals(8, result.get(2));
        assertEquals(16, result.get(3));
        assertEquals(32, result.get(4));
    }
                                    
}