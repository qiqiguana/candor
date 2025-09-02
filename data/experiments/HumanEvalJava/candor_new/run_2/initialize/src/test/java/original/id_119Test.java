package original;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
/**
* Test class of MatchParens.
*/
class MatchParensTest {
    @Test
    void testMatchParensYes1() {
        List<String> list = new ArrayList<>();
        list.add("(()(())");
        list.add(")())()");
        assertEquals("No", MatchParens.matchParens(list));
    }
}
