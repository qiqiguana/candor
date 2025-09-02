package original;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
* Test class of MyCircularDeque.
*/
class MyCircularDequeTest {
    @Test
    void testInsertFront() {
        // Arrange
        int capacity = 5;
        MyCircularDeque myCircularDeque = new MyCircularDeque(capacity);

        // Act
        boolean result1 = myCircularDeque.insertFront(7);
        boolean result2 = myCircularDeque.insertFront(0);

        // Assert
        assertTrue(result1);
        assertTrue(result2);
    }
}