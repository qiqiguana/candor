package original;

import java.util.List;
import java.util.ArrayList;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
/**
* Test class of MatchParens.
*/
class MatchParensTest {
    @Test
    void testMatchParens_CompatibleStrings_ReturnsYes() {
        List<String> list = new ArrayList<>();
        list.add("(");
        list.add(")");
        String result = MatchParens.matchParens(list);
        assertEquals("Yes", result);
    }
}