package oracle1;

import java.util.Arrays;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
* Test class of MoveOneBall.
*/
class MoveOneBallTest {
    @Test
    void testMoveOneBall_EmptyArray_ReturnsTrue() {
        List<Object> arr = List.of();
        assertTrue(MoveOneBall.moveOneBall(arr));
    }
    
    @Test
     void testNothing(){
         MoveOneBall s = new MoveOneBall();
         }
    @Test
    public void testEmptyArray() {
        List<Object> arr = new ArrayList<>();
        assertTrue(MoveOneBall.moveOneBall(arr));
    }
    @Test
    public void testSingleElementArray() {
        List<Object> arr = Arrays.asList(1);
        assertTrue(MoveOneBall.moveOneBall(arr));
    }
    @Test
    public void testAlreadySortedArrayFixed() {
        List<Object> arr = Arrays.asList(1, 2, 3, 4, 5);
        assertFalse(MoveOneBall.moveOneBall(arr));
    }
    @Test
    public void testUnsortedArrayWithOneShiftNeeded() {
        List<Object> arr = Arrays.asList(3, 4, 5, 1, 2);
        assertTrue(MoveOneBall.moveOneBall(arr));
    }
    @Test
    public void testUnsortedArrayWithNegativeElements2() {
        List<Object> arr = Arrays.asList(-1, -2, -3, 1, 2);
        assertFalse(MoveOneBall.moveOneBall(arr));
    }
                                  
}