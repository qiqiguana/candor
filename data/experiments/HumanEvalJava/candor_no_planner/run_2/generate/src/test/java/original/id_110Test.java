package original;

import java.util.Arrays;
import java.util.List;
import java.util.ArrayList;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
/**
* Test class of Exchange.
*/
class ExchangeTest {
    @Test
    void test_exchange_even() {
        List<Integer> list1 = new ArrayList<>(List.of(1, 2, 3, 4));
        List<Integer> list2 = new ArrayList<>(List.of(1, 2, 3, 4));
        assertEquals("YES", Exchange.exchange(list1, list2));
    }
    
    @Test
        public void testNothing(){
            Exchange s = new Exchange();
            }
    @Test
    void testExchange_OddCountInLst1_NoEvenNumberInLst2() {
        List<Integer> lst1 = new ArrayList<>(Arrays.asList(1, 3));
        List<Integer> lst2 = new ArrayList<>(Arrays.asList(5, 7));
        String result = Exchange.exchange(lst1, lst2);
        assertEquals("NO", result);
    }
                                    
}