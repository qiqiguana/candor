package original;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
/**
* Test class of Add1.
*/
class Add1Test {
    @Test
    void testAdd_SingleEvenElementAtOddIndex_ReturnsEvenElement() {
        List<Integer> lst = new ArrayList<>();
        lst.add(4);
        lst.add(2);
        assertEquals(2, Add1.add(lst));
    }
}
