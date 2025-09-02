package original;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
/**
* Test class of IsSorted.
*/
class IsSortedTest {
    @Test
    void isSorted_EmptyList_ReturnsTrue() {
        List<Object> lst = new ArrayList<>();
        assertTrue(IsSorted.isSorted(lst));
    }
}