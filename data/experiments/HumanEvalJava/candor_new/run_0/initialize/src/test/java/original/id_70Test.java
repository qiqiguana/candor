package original;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
* Test class of StrangeSortList.
*/
class StrangeSortListTest {
    @Test
    void testStrangeSortList_EmptyList_ReturnsEmptyList() {
        // Arrange
        List<Object> input = new ArrayList<>();
        
        // Act
        List<Object> result = StrangeSortList.strangeSortList(input);
        
        // Assert
        assertTrue(result.isEmpty());
    }
}