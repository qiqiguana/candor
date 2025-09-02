package original;

import java.util.List;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
/**
* Test class of MatchParens.
*/
class MatchParensTest {

    @Test
    void testMatchParens() {
        List<String> input = List.of("(", ")");
        assertEquals("Yes", MatchParens.matchParens(input));
    }
}