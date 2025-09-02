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
    void testSortEven_SortedEvenIndices() {
        // Arrange
        List<Integer> inputList = new ArrayList<>(List.of(5, 3, -5, 2, -3, 3, 9, 0, 123, 1, -10));
        List<Integer> expectedList = new ArrayList<>(List.of(-10, 3, -5, 2, -3, 3, 5, 0, 9, 1, 123));

        // Act
        List<Integer> actualList = SortEven.sortEven(inputList);

        // Assert
        assertEquals(expectedList, actualList);
    }
}