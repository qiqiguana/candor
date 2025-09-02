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
    void testMoveOneBallWithSortedArray() {
        List<Object> arr = new ArrayList<>();
        arr.add(3);
        arr.add(4);
        arr.add(5);
        arr.add(1);
        arr.add(2);
        assertTrue(MoveOneBall.moveOneBall(arr));
    }
    
    @Test
        public void testNothing(){
            MoveOneBall s = new MoveOneBall();
            }
    @Test
    public void test_moveOneBall_with_empty_array() {
        List<Object> input = new ArrayList<>();
        assertTrue(MoveOneBall.moveOneBall(input));
    }
    @Test
    public void test_move_one_ball_false_when_count_greater_than_1() {
        java.util.List<java.lang.Object> arr = new java.util.ArrayList<>(java.util.Arrays.asList(3, 5, 10, 2, 4));
        assertFalse(MoveOneBall.moveOneBall(arr));
    }
                                    
}