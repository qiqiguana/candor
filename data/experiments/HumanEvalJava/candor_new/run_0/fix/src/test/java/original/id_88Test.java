package original;

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
        // Arrange
        List<Object> array = new ArrayList<>();
        array.add(2);
        array.add(4);
        array.add(3);
        array.add(0);
        array.add(1);
        array.add(5);
        
        // Act
        List<Object> result = SortArray1.sortArray(array);
        
        // Assert
        assertEquals(result, Arrays.asList(0, 1, 2, 3, 4, 5));
    }
    
    @Test
        public void testNothing(){
            SortArray1 s = new SortArray1();
            }
    @Test
    public void TestEmptyArray() {
        List<Object> input = new ArrayList<>();
        assertEquals(input, SortArray1.sortArray(input));
    }
    @Test
    public void TestEvenSumSortDescending() {
        List<Object> input = new ArrayList<>();
        input.add(2);
        input.add(4);
        input.add(3);
        input.add(0);
        input.add(1);
        input.add(5);
        input.add(6);
        List<Object> expected = new ArrayList<>();
        expected.add(6);
        expected.add(5);
        expected.add(4);
        expected.add(3);
        expected.add(2);
        expected.add(1);
        expected.add(0);
        assertEquals(expected, SortArray1.sortArray(input));
    }
                                    
}