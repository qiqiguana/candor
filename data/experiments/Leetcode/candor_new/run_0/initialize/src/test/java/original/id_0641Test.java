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
        int k = 3;
        MyCircularDeque myCircularDeque = new MyCircularDeque(k);
        int value = 1;
        
        // Act
        boolean result = myCircularDeque.insertFront(value);
        
        // Assert
        assertTrue(result);
    }
}