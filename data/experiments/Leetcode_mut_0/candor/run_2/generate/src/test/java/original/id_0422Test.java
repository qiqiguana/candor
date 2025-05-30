package original;

import java.util.List;
import java.util.ArrayList;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
/**
* Test class of Solution0422.
*/
class Solution0422Test {

    @Test
    void testValidWordSquare() {
        Solution0422 solution = new Solution0422();
        List<String> words = new ArrayList<>();
        words.add("abc");
        words.add("ba");
        assertFalse(solution.validWordSquare(words));
    }
}