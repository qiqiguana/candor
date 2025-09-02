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
    void testSortArray_SumOfFirstAndLastElementIsOdd_ReturnsSortedAscending() {
        // Arrange
        List<Object> array = new ArrayList<>(List.of(2, 4, 3, 0, 1, 5));
        // Act
        List<Object> sortedArray = SortArray1.sortArray(array);
        // Assert
        assertIterableEquals(List.of(0, 1, 2, 3, 4, 5), sortedArray);
    }
}