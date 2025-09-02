package original;

import java.util.List;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
* Test class of MaxFill.
*/
class MaxFillTest {

    @Test
    void testMaxFill_BucketCapacityIsOne_ExpectedSix() {
        List<List<Integer>> grid = List.of(
            List.of(0, 0, 1, 0), 
            List.of(0, 1, 0, 0),
            List.of(1, 1, 1, 1)
        );
        int capacity = 1;
        assertEquals(6, MaxFill.maxFill(grid, capacity));
    }
}