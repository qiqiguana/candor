package original;

import java.util.List;
import java.util.ArrayList;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
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