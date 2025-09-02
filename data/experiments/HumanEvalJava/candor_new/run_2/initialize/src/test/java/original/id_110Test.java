package original;

import java.util.List;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
* Test class of Exchange.
*/
class ExchangeTest {

    @Test
    void test_exchange_should_return_YES_when_lst1_has_odd_numbers_and_lst2_has_even_numbers() {
        List<Integer> lst1 = List.of(1, 2, 3, 4);
        List<Integer> lst2 = List.of(2, 6, 4);
        String result = Exchange.exchange(lst1, lst2);
        assertEquals("YES", result);
    }
}