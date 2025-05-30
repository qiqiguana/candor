package original;

import java.util.List;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import java.util.ArrayList;
/**
* Test class of Solution0422.
*/
class Solution0422Test {
    @Test
    void testValidWordSquare() {
        Solution0422 solution = new Solution0422();
        List<String> words = new ArrayList<>();
        words.add("abc");
        words.add("bca");
        words.add("cab");
        assertTrue(solution.validWordSquare(words));
    }
}