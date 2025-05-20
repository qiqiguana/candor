package original;

import java.util.List;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import java.util.ArrayList;
/**
* Test class of Skjkasdkd.
*/
class SkjkasdkdTest {
    @Test
    void testSkjkasdkd() {
        List<Integer> list = new ArrayList<>();
        list.add(0);
        list.add(724);
        list.add(32);
        list.add(71);
        list.add(99);
        list.add(32);
        list.add(6);
        list.add(0);
        list.add(5);
        list.add(91);
        list.add(83);
        list.add(0);
        list.add(5);
        list.add(6);
        assertEquals(11, Skjkasdkd.skjkasdkd(list));
    }
}