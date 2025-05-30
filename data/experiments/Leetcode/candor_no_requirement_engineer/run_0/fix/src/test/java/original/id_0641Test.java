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
        MyCircularDeque myCircularDeque = new MyCircularDeque(3);
        
        // Act
        boolean result1 = myCircularDeque.insertLast(1);
        boolean result2 = myCircularDeque.insertLast(2);
        boolean result3 = myCircularDeque.insertFront(3);
        boolean result4 = myCircularDeque.insertFront(4);
        
        // Assert
        assertTrue(result1);
        assertTrue(result2);
        assertTrue(result3);
        assertFalse(result4);
    }
}