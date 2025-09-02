package original;

import java.util.List;
import java.util.ArrayList;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
* Test class of Maximum1.
*/
class Maximum1Test {

    @Test
    void testMaximum_KIsEqualToArraySize() {
        List<Integer> arr = new ArrayList<>(List.of(5, 15, 0, 3, -13, -8, 0));
        int k = 7;
        List<Object> expected = new ArrayList<>(List.of(-13, -8, 0, 0, 3, 5, 15));
        assertEquals(expected, Maximum1.maximum(arr, k));
    }
}