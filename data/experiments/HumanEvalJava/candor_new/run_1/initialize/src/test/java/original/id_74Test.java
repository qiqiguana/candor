package original;

import java.util.List;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
/**
* Test class of TotalMatch.
*/
class TotalMatchTest {
    @Test
    void testTotalMatch_ReturnsListWithLessChars() {
        List<Object> list1 = List.of("hi", "admin");
        List<Object> list2 = List.of("hI", "Hi");
        assertEquals(list2, TotalMatch.totalMatch(list1, list2));
    }
}