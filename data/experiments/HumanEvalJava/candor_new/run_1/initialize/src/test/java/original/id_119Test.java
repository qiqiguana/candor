package original;

import java.util.List;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
/**
* Test class of MatchParens.
*/
class MatchParensTest {
    @Test
    void matchParens_should_ReturnYes_When_S1S2AndS2S1AreBalanced() {
        List<String> lst = List.of("(",")");
        String result = MatchParens.matchParens(lst);
        assertEquals("Yes", result);
    }
}