package original;

import java.util.List;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import java.util.ArrayList;
/**
* Test class of Minsubarraysum.
*/
class MinsubarraysumTest {
    @Test
    void testMinSubArraySum_EmptyList_ReturnsZero() {
        List<Object> nums = new ArrayList<>();
        assertThrows(IndexOutOfBoundsException.class, () -> Minsubarraysum.minsubarraysum(nums));
    }
}