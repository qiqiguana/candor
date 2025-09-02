package original;

import java.util.Deque;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
/**
* Test class of MyCircularDeque.
*/
class MyCircularDequeTest {
    @Test
    void test_insertFront_WhenQueueIsEmpty_ReturnsTrue() {
        // Arrange
        MyCircularDeque myCircularDeque = new MyCircularDeque(5);
        int value = 1;
        
        // Act
        boolean result = myCircularDeque.insertFront(value);
        
        // Assert
        assertTrue(result);
    }
}