package original;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
/**
* Test class of CanArrange.
*/
class CanArrangeTest {
    @Test
    void test_can_arrange_1() {
        List<Object> list = new ArrayList<>();
        list.add(1);
        list.add(2);
        list.add(4);
        list.add(3);
        list.add(5);
        assertEquals(3, CanArrange.canArrange(list));
    }
}