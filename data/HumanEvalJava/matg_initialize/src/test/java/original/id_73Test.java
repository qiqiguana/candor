package original;

import java.util.List;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
/**
* Test class of SmallestChange.
*/
class SmallestChangeTest {
    @Test
    void testSmallestChange_PalindromicArray_ReturnsZero() {
        // Arrange
        List<Integer> palindromicArray = List.of(1, 2, 3, 2, 1);

        // Act
        int result = SmallestChange.smallestChange(palindromicArray);

        // Assert
        assertEquals(0, result);
    }
}