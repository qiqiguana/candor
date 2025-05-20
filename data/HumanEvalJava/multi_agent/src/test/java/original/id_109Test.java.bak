package original;

import java.util.Arrays;
import java.util.List;
import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;
/**
* Test class of MoveOneBall.
*/
class MoveOneBallTest {
    @Test
    void testMoveOneBall() {
        // TODO implement the test
    }
    @Test
    public void testEmptyList() {
        List<Object> arr = Arrays.asList();
        Boolean result = MoveOneBall.moveOneBall(arr);
        assert result;
    }
    @Test
    public void testSortableList() {
        List<Object> arr = Arrays.asList(3, 4, 5, 1, 2);
        Boolean result = MoveOneBall.moveOneBall(arr);
        assert result;
    }
    @Test
    public void testUnsortableList() {
        List<Object> arr = Arrays.asList(3, 5, 4, 1, 2);
        Boolean result = MoveOneBall.moveOneBall(arr);
        assert !result;
    }
    @Test
    public void testSingleElementList() {
        List<Object> arr = Arrays.asList(1);
        Boolean result = MoveOneBall.moveOneBall(arr);
        assert result;
    }
}