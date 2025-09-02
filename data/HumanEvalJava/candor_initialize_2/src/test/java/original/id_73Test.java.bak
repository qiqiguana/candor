package original;

import java.util.List;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
/**
* Test class of SmallestChange.
*/
class SmallestChangeTest {

    @Test
    void test_smallestChange_symmetric_array() {
        List<Integer> arr = List.of(1, 2, 3, 2, 1);
        int expected = 0;
        assertEquals(expected, SmallestChange.smallestChange(arr));
    }
    @Test
    public void testSmallestChangeWithNullInput() {
        List<Integer> arr = null;
        assertThrows(NullPointerException.class, () -> SmallestChange.smallestChange(arr));
    }
    @Test
    public void TestSmallestChange_NullArray() {
        assertThrows(NullPointerException.class, () -> SmallestChange.smallestChange(null));
    }
    @Test
    public void testSmallestChange_SadPath_NullArray() {
        List<Integer> arr = null;
        assertThrows(NullPointerException.class, () -> SmallestChange.smallestChange(arr));
    }
}