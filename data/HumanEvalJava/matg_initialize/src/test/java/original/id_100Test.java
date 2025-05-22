package original;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
* Test class of MakeAPile.
*/
class MakeAPileTest {
    @Test
    void testMakeAPile_OddNumber() {
        List<Integer> expected = new ArrayList<>();
        expected.add(3);
        expected.add(5);
        expected.add(7);
        assertEquals(expected, MakeAPile.makeAPile(3));
    }
}
