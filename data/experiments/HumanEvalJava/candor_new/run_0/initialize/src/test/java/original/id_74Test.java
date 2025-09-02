package original;

import java.util.List;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
/**
* Test class of TotalMatch.
*/
class TotalMatchTest {
    @Test
    void testTotalMatch() {
        List<Object> lst1 = List.of("hi", "admin");
        List<Object> lst2 = List.of("hI", "Hi");
        assertEquals(lst2, TotalMatch.totalMatch(lst1, lst2));
    }
}
