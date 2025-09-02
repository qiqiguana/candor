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
}