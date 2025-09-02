package original;

import java.util.List;
import java.util.ArrayList;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
/**
* Test class of Add1.
*/
class Add1Test {

    @Test
    void testAdd_88() {
        List<Integer> lst = new ArrayList<>();
        lst.add(4);
        lst.add(88);
        assertEquals(88, Add1.add(lst));
    }
}