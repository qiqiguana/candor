package original;

import java.util.List;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
* Test class of Monotonic.
*/
class MonotonicTest {
    @Test
    void test_Monotonic_IncreasingList_ReturnsTrue() {
        // Arrange
        List<Integer> l = List.of(1, 2, 3, 4, 5);
        Boolean expected = true;
        
        // Act
        Boolean result = Monotonic.monotonic(l);
        
        // Assert
        assertEquals(expected, result);
    }
}