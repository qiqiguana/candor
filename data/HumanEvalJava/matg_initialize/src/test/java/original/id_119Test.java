package original;

import java.util.List;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
/**
* Test class of MatchParens.
*/
class MatchParensTest {

    @Test
    void test_matchParens_EmptyStrings_ReturnsYes() {
        List<String> input = List.of("", "");
        String result = MatchParens.matchParens(input);
        assertEquals("Yes", result);
    }
}