package original;
import java.util.List;
import java.util.ArrayList;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
/**
* Test class of TotalMatch.
*/
class TotalMatchTest {
    @Test
    void testTotalMatch() {
        List<Object> lst1 = new ArrayList<>();
        lst1.add("hi");
        lst1.add("admin");
        List<Object> lst2 = new ArrayList<>();
        lst2.add("hI");
        lst2.add("Hi");
        assertEquals(lst2, TotalMatch.totalMatch(lst1, lst2));
    }
}