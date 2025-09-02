package original;

import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
/**
* Test class of ReverseDelete.
*/
class ReverseDeleteTest {
    @Test
    void test_reverse_delete_single_character() {
        String s = "a";
        String c = "a";
        List<Object> expected = Arrays.asList("", true);
        assertEquals(expected, ReverseDelete.reverseDelete(s, c));
    }
}