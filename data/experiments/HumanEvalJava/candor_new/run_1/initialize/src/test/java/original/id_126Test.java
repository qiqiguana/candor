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
    void testIsSorted_DuplicateElements_ReturnsFalse() {
        List<Object> lst = new ArrayList<>();
        lst.add(1);
        lst.add(2);
        lst.add(2);
        lst.add(2);
        lst.add(3);
        lst.add(4);
        assertFalse(IsSorted.isSorted(lst));
    }
}