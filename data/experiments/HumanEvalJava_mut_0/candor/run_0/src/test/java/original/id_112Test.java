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
void testReverseDeleteShouldReturnResultAndIsPalindrome1() {
        String s = "madam";
        String c = "a";
        List<Object> result = ReverseDelete.reverseDelete(s, c);
        assertEquals("mdm", result.get(0));
    }
}
