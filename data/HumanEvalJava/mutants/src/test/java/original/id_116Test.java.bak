package original;

import java.util.Arrays;
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
    public void testSortArray_EmptyList_ReturnsEmptyList() {
        // Arrange
        List<Object> input = new ArrayList<>();
        
        // Act
        List<Object> result = SortArray.sortArray(input);
        
        // Assert
        assertTrue(result.isEmpty());
    }
    
    @Test
        public void testNothing(){
            SortArray s = new SortArray();
            }
    @Test
    public void testEmptyArray() {
        List<Object> arr = new ArrayList<>();
        List<Object> result = SortArray.sortArray(arr);
        assertTrue(result.isEmpty());
    }
    @Test
    public void testSingleElementArray() {
        List<Object> arr = new ArrayList<>(Arrays.asList(5));
        List<Object> result = SortArray.sortArray(arr);
        assertEquals(Arrays.asList(5), result);
    }
    @Test
    public void testArrayWithLargeNumbers2() {
        List<Object> arr = new ArrayList<>(Arrays.asList(32, 44, 12, 6));
        List<Object> result = SortArray.sortArray(arr);
        assertEquals(Arrays.asList(32, 6, 12, 44), result);
    }
                                    
}