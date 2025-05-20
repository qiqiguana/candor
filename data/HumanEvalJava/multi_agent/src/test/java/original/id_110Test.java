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
    void test_exchange_shouldReturnYES_when_lst1IsMadeOfEvenNumbersAfterExchange() {
        List<Integer> lst1 = new ArrayList<>(List.of(1, 2, 3, 4));
        List<Integer> lst2 = new ArrayList<>(List.of(2, 6, 4));
        String result = Exchange.exchange(lst1, lst2);
        assertEquals("YES", result);
    }
}