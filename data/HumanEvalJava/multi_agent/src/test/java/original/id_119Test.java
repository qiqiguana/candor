package original;

import org.junit.jupiter.api.Test; import static org.junit.jupiter.api.Assertions.assertEquals;
import java.util.List;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
* Test class of MatchParens.
*/
class MatchParensTest {

    @Test
    void testMatchParens_balanced() {
        List<String> input = List.of("(", ")");
        String expected = "Yes";
        assertEquals(expected, MatchParens.matchParens(input));
    }
    @Test public void testUncoveredLinesLine62() { List<String> input = java.util.Arrays.asList("((", ")"); assertEquals("No", original.MatchParens.matchParens(input)); }
    @Test public void testUncoveredLinesLine6876() { List<String> input = java.util.Arrays.asList(")())", "("); assertEquals("No", original.MatchParens.matchParens(input)); }
}