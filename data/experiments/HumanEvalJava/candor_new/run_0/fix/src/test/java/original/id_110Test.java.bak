package original;

import java.util.Arrays;
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
    
    @Test
        public void testNothing(){
            Exchange s = new Exchange();
            }
    @Test
    public void test_exchange_only_even_numbers_5() {
        List<Integer> list1 = Arrays.asList(100, 200);
        List<Integer> list2 = Arrays.asList(200, 200);
        String result = Exchange.exchange(list1, list2);
        assertEquals("YES", result);
    }
    @Test
    public void TestExchangeWithInsufficientEvenNumbersInLst21() {
        List<Integer> lst1 = Arrays.asList(3, 2, 6, 1, 8, 9);
        List<Integer> lst2 = Arrays.asList(3, 5, 5, 1, 1, 1);
        String result = Exchange.exchange(lst1, lst2);
        assertEquals("NO", result);
    }
                                    
}