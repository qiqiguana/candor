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
    public void testSortArray_EmptyList_ReturnsEmptyList() {
        // Arrange
        List<Object> input = new ArrayList<>();
        
        // Act
        List<Object> result = SortArray.sortArray(input);
        
        // Assert
        assertTrue(result.isEmpty());
    }
}
