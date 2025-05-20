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
    void testMakeAPile_EvenNumber_ReturnsCorrectList() {
        int n = 4;
        List<Integer> expected = new ArrayList<>(List.of(4, 6, 8, 10));
        assertEquals(expected, MakeAPile.makeAPile(n));
    }
}
