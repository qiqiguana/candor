package original;

import java.util.List;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
* Test class of NextSmallest.
*/
class NextSmallestTest {
    @Test
    void testNextSmallest() {
        List<Object> lst = List.of(1, 2, 3, 4, 5);
        Integer result = NextSmallest.nextSmallest(lst);
        assertEquals(Integer.valueOf(2), result);
    }
}