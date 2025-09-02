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
    void testIsSorted_DuplicateMoreThanTwice_ReturnsFalse() {
        List<Object> list = new ArrayList<>();
        list.add(1);
        list.add(2);
        list.add(2);
        list.add(2);
        list.add(3);
        assertFalse(IsSorted.isSorted(list));
    }
}