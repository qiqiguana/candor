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
    void test_move_one_ball_when_array_is_empty() {
        List<Object> arr = new ArrayList<>();
        assertTrue(MoveOneBall.moveOneBall(arr));
    }
    
    @Test
        public void testNothing(){
            MoveOneBall s = new MoveOneBall();
            }
    @Test
    public void testMoveOneBall_SingleElementArray() {
        List<Object> arr = new ArrayList<>();
        arr.add(1);
        assertTrue(MoveOneBall.moveOneBall(arr));
    }
    @Test
    public void testMoveOneBall_SortedArray() {
        List<Object> arr = new ArrayList<>();
        arr.add(1);
        arr.add(2);
        arr.add(3);
        arr.add(4);
        arr.add(5);
        assertTrue(MoveOneBall.moveOneBall(arr));
    }
    @Test
    public void testMoveOneBall_UnsortedArray1_Fixed() {
        List<Object> arr = new ArrayList<>();
        arr.add(1);
        arr.add(2);
        arr.add(3);
        arr.add(5);
        arr.add(4);
        assertFalse(MoveOneBall.moveOneBall(arr));
    }
                                    
}