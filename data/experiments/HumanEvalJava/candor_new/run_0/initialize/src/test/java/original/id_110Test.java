package original;

import java.util.List;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
/**
* Test class of Exchange.
*/
class ExchangeTest {
    @Test
    void testExchangeShouldReturnYesWhenEnoughEvenNumbersAreAvailableInList2() {
        List<Integer> list1 = List.of(1, 3);
        List<Integer> list2 = List.of(4, 6);
        String result = Exchange.exchange(list1, list2);
        assertEquals("YES", result);
    }
}
