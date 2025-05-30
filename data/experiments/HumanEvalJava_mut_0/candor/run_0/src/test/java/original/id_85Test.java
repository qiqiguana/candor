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
    void testAdd_withEvenIndexOddValues() {
        List<Integer> list = new ArrayList<>();
        list.add(2);
        list.add(3);
        list.add(4);
        assertEquals(0, Add1.add(list));
    }
}