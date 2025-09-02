package original;

import java.util.List;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import java.util.ArrayList;
/**
* Test class of MoveOneBall.
*/
class MoveOneBallTest {
    @Test
    void testMoveOneBall_EmptyArray_ReturnsTrue() {
        // Arrange
        List<Object> arr = new ArrayList<>();
        // Act
        Boolean result = MoveOneBall.moveOneBall(arr);
        // Assert
        assertTrue(result);
    }
}