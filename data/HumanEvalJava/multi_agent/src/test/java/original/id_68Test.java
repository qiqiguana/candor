package original;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
/**
* Test class of Pluck.
*/
class PluckTest {
    @Test
    void testPluck_SmallestEvenValue_ReturnsSmallestValueAndIndex() {
        List<Object> arr = new ArrayList<>();
        arr.add(5);
        arr.add(0);
        arr.add(3);
        arr.add(0);
        arr.add(4);
        arr.add(2);

        List<Object> result = Pluck.pluck(arr);

        assertEquals(2, result.size());
        assertEquals(0, result.get(0));
        assertEquals(1, result.get(1));
    }
}