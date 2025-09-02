package original;

import java.util.*;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
/**
* Test class of Maximum1.
*/
class Maximum1Test {
    @Test
    void testMaximum_KIsEqualToArraySize_ReturnsSortedArray() {
        // Arrange
        List<Integer> arr = new ArrayList<>(Arrays.asList(5, 15, 0, 3, -13, -8, 0));
        int k = 7;
        // Act
        List<Object> result = Maximum1.maximum(arr, k);
        // Assert
        assertEquals(Arrays.asList(-13, -8, 0, 0, 3, 5, 15), result);
    }
}