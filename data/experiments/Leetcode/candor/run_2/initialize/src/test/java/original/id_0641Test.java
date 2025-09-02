package original;

import java.util.Deque;

import java.util.Set;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
/**
* Test class of MyCircularDeque.
*/
class MyCircularDequeTest {

    @Test
    void testInsertFront() {
        MyCircularDeque deque = new MyCircularDeque(3);
        assertTrue(deque.insertFront(1));
    }
}