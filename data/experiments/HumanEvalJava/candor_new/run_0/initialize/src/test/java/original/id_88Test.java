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
        assertEquals(result.get(0), 0);
    }
}