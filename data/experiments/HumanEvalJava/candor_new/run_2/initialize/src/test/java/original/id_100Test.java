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
    void testMakeAPile_OddInput_ReturnsCorrectList() {
        int n = 3;
        List<Integer> expected = new ArrayList<>(List.of(3, 5, 7));
        assertEquals(expected, MakeAPile.makeAPile(n));
    }
}