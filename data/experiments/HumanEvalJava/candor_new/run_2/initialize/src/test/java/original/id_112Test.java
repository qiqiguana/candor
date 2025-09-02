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
    void testReverseDelete() {
        List<Object> result = ReverseDelete.reverseDelete("abcdedcba", "ab");
        assertEquals(true, result.get(1));
    }
}