package original;

import java.util.List;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import java.util.ArrayList; 
/**
* Test class of NextSmallest.
*/
class NextSmallestTest {
    @Test
    void next_smallest_with_empty_list_returns_null_1() {
        // Arrange
        List<Object> list = new ArrayList<>();
        // Act and Assert
        assertNull(NextSmallest.nextSmallest(list));
    }
}