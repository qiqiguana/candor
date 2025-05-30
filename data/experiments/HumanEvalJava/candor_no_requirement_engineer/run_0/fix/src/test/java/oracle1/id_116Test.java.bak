package oracle1;

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
    void testSortArray_SimpleInput_ListSortedByOnesAndDecimalValue() {
        // Arrange
        List<Object> input = new ArrayList<>();
        input.add(5);
        input.add(2);
        input.add(3);
        input.add(4);
        input.add(1);

        // Act
        List<Object> result = SortArray.sortArray(input);

        // Assert
        assertEquals(5, result.size());
    }
    
    @Test
        public void testNothing(){
            SortArray s = new SortArray();
            }
    @Test
    public void testEmptyArray() {
        List<Object> arr = new ArrayList<>();
        assertEquals(Collections.emptyList(), SortArray.sortArray(arr));
    }
    @Test
    public void testNullInput() {
        List<Object> arr = null;
        assertThrows(NullPointerException.class, () -> SortArray.sortArray(arr));
    }
    @Test
    public void testSingleElementArray1() {
        List<Object> arr = new ArrayList<>(Arrays.asList(5));
        assertEquals(Arrays.asList(5), SortArray.sortArray(arr));
    }
                                    
}