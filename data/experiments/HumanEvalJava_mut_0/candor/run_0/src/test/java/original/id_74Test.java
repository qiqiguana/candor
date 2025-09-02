package original;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
/**
* Test class of TotalMatch.
*/
class TotalMatchTest {

@Test
void testTotalMatch_WhenSum1LessThanOrEqualToSum2_ReturnEmptyList() {
    List<Object> lst1 = new ArrayList<>();
    lst1.add("hello");
    lst1.add(123);
    lst1.add(true);
    List<Object> lst2 = new ArrayList<>();
    lst2.add("world");
    List<Object> result = TotalMatch.totalMatch(lst1, lst2);
    assertTrue(result.isEmpty());
}
}