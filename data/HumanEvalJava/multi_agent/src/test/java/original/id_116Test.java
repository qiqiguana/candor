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
    public void testSortArray() {
        List<Integer> list = new ArrayList<>();
        list.add(5);
        list.add(2);
        list.add(8);
        list.add(1);
        list.add(9);
        Collections.sort(list, Comparator.naturalOrder());
        assertEquals(true, isSorted(list));
    }
    @Test
    public void testEmptyList() {
        List<Object> arr = new ArrayList<>();
        assertEquals(Collections.emptyList(), SortArray.sortArray(arr));
    }
    @Test
    public void testSortArrayWithZero() {
    	List<Object> arr = new ArrayList<>();
    	arr.add(0);
    	arr.add(2);
    	arr.add(4);
    	arr.add(8);
    	List<Object> expected = new ArrayList<>();
    	expected.add(0);
    	expected.add(2);
    	expected.add(4);
    	expected.add(8);
    	assertEquals(expected, SortArray.sortArray(arr));
    }
    @Test
    public void testSortArrayWithSameOnes() {
    	List<Object> arr = new ArrayList<>();
    	arr.add(5);
    	arr.add(3);
    	arr.add(9);
    	arr.add(13);
    	List<Object> expected = new ArrayList<>();
    	expected.add(3);
    	expected.add(5);
    	expected.add(9);
    	expected.add(13);
    	assertEquals(expected, SortArray.sortArray(arr));
    }

    public boolean isSorted(List<Integer> list) {
        for (int i = 0; i < list.size() - 1; i++) {
            if (list.get(i) > list.get(i + 1)) {
                return false;
            }
        }
        return true;
    }
}