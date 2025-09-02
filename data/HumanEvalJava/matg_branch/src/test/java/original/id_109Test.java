package original;

import java.util.Arrays;
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
        List<Object> arr = new ArrayList<>();
        Boolean result = MoveOneBall.moveOneBall(arr);
        assertTrue(result);
    }
    
    @Test
        public void testNothing(){
            MoveOneBall s = new MoveOneBall();
            }
    @Test
    public void testEmptyList() {
        assertTrue(MoveOneBall.moveOneBall(new ArrayList<>()));
    }
    @Test
    public void testSingleElementList() {
        assertTrue(MoveOneBall.moveOneBall(Arrays.asList(1)));
    }
    @Test
    public void testAlreadySortedList() {
        assertTrue(MoveOneBall.moveOneBall(Arrays.asList(1, 2, 3, 4, 5)));
    }
    @Test
    public void testReverseSortedList() {
        assertFalse(MoveOneBall.moveOneBall(Arrays.asList(5, 4, 3, 2, 1)));
    }
    @Test
    public void testUnsortedListWithoutRotation() { assertFalse(MoveOneBall.moveOneBall(List.of(3, 5, 4, 1, 2))); }
                                    
}