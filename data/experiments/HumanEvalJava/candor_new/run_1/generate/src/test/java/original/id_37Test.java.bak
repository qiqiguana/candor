package original;

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
    void testSortEven_SimpleList_ReturnsSortedEvenIndices() {
        // Arrange
        List<Integer> list = new ArrayList<>(List.of(5, 6, 3, 4));
        List<Integer> expected = List.of(3, 6, 5, 4);

        // Act
        List<Integer> actual = SortEven.sortEven(list);

        // Assert
        assertEquals(expected, actual);
    }
}