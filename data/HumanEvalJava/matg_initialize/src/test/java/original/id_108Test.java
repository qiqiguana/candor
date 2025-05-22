package original;

import java.util.List;
import java.util.ArrayList;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
/**
* Test class of CountNums.
*/
class CountNumsTest {
    @Test
    void testCountNums_EmptyList_ReturnsZero() {
        List<Object> arr = new ArrayList<>();
        int result = CountNums.countNums(arr);
        assertEquals(0, result);
    }
}