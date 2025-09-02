package original;

import java.util.List;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
* Test class of CountNums.
*/
class CountNumsTest {
    @Test
    void testCountNums_EmptyList_ReturnZero() {
        List<Object> arr = List.of();
        int result = CountNums.countNums(arr);
        assertEquals(0, result);
    }
}