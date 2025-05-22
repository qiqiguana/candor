package original;

import java.util.List;
import java.util.ArrayList;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
/**
* Test class of Exchange.
*/
class ExchangeTest {
    @Test
    void testExchangeEvenNumbers() {
        List<Integer> lst1 = new ArrayList<>();
        lst1.add(1);
        lst1.add(2);
        lst1.add(3);
        lst1.add(4);
        List<Integer> lst2 = new ArrayList<>();
        lst2.add(1);
        lst2.add(2);
        lst2.add(3);
        lst2.add(4);
        assertEquals("YES", Exchange.exchange(lst1, lst2));
    }
}