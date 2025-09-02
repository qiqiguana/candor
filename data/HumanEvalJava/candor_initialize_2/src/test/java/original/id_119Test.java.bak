package original;

import java.util.List;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
* Test class of MatchParens.
*/
class MatchParensTest {
    @Test
    void testMatchParens_BalancedString_ReturnsYes() {
        List<String> input = List.of("(", ")");
        String result = MatchParens.matchParens(input);
        assertEquals("Yes", result);
    }
    @Test
    public void testMatchParens_NullInput() {
        List<String> input = null;
        assertThrows(NullPointerException.class, () -> MatchParens.matchParens(input));
    }
}